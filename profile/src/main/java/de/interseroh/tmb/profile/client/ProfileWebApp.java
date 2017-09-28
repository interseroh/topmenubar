/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package de.interseroh.tmb.profile.client;

import java.util.Collection;
import java.util.logging.Logger;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import de.interseroh.tmb.user.client.UserInfoResponse;
import de.interseroh.tmb.user.client.UserInformationService;
import de.interseroh.tmb.user.client.UserInformationServiceImpl;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.base.ComplexWidget;

public class ProfileWebApp implements EntryPoint {

	private static final String DATA_APPLICATION_URL = "data-tmb-sso-url";
	private static final String DATA_USER_INFORMATION_URL = "data-tmb-user-info";
	private static final String DATA_USER_COOKIE_PATH = "data-tmb-cookie-path";
	private static final String SSO_FALLBACK="http://localhost:9000/ep/openid_connect_login?identifier=http%3A%2F%2Flocalhost%3A8080%2Fopenid-connect-server-webapp%2F";
	private static final String USERINFO_FALLBACK="http://localhost:9000/ep/";

	private static final String TMB_PROFILE = "tmb_profile";
	private RootPanel profile;

	private static final Logger logger = Logger
			.getLogger(ProfileWebApp.class.getName());

	private Messages messages;


	@Override
	public void onModuleLoad() {
		logger.info("ProfileWebApp: Create Views begins...");

		logger.info("Get Profile Widget...");
		profile = getWidgets(TMB_PROFILE);

		String ssoUrl = getSsoUrl();
		String userInfoUrl = getUserInfoUrl();
		String cookiePath  = getCookiePath();

		messages = GWT.create(Messages.class);

		UserInformationService userInformationService = new UserInformationServiceImpl(ssoUrl, userInfoUrl, cookiePath);

		handleCookies();

		logger.info("Checking login state...");
		if (userInformationService.isLoggedIn()) {
			setLoggedInState(userInformationService);
		} else {
			setLoggedOutState(userInformationService);
		}

		logger.info("ProfileWebApp: Create Views ends...");
	}

	private String getSsoUrl() {
		String ssoUrl = profile.getElement()
				.getAttribute(DATA_APPLICATION_URL);

		if (ssoUrl == null || ssoUrl.trim().isEmpty()) {
			logger.info("FALLING BACK TO SSO URL "+SSO_FALLBACK);
			ssoUrl=SSO_FALLBACK;
		} else{
			logger.info("USING SSO SERVICE "+ssoUrl);
		}
		return ssoUrl;
	}

	private String getUserInfoUrl() {
		String userInfoUrl = profile.getElement()
				.getAttribute(DATA_USER_INFORMATION_URL);

		if (userInfoUrl == null || userInfoUrl.trim().isEmpty()) {
			logger.info("FALLING BACK TO USER INFO URL "+USERINFO_FALLBACK);
			userInfoUrl=SSO_FALLBACK;
		} else{
			logger.info("USING USER INFO URL "+userInfoUrl);
		}
		return userInfoUrl;
	}

	private String getCookiePath() {
		String cookiePath = profile.getElement()
				.getAttribute(DATA_USER_COOKIE_PATH);

		if (cookiePath == null || cookiePath.trim().isEmpty()) {
			logger.warning("NO COOKIE PATH HAS BEEN SET. NULLIFYING IT!");
			cookiePath = null;
		} else{
			logger.info("USING COOKIE PATH "+cookiePath+" FOR JSESSIONID COOKIE HANDLING");
		}
		return cookiePath;
	}

	private void setLoggedInState(UserInformationService userInformationService){

		profile.getElement().addClassName("user-logged-in");
		logger.info("WE ARE LOGGED IN");

		MethodCallback<UserInfoResponse> clientCallBack = new MethodCallback<UserInfoResponse>() {
			@Override
			public void onFailure(Method method, Throwable throwable) {
				logger.severe("Retrieval of userinfo failed! Initiate Bailout sequence");
				setLoggedOutState(userInformationService);
				logger.severe("done.");
			}

			@Override
			public void onSuccess(Method method, final UserInfoResponse response) {
				logger.info("I got user "+response.getUsername()+" who is logged in.");
				profile.getElement().setTitle(messages.loggedInAs()+response.getUsername());

				eventualChildNodeRemoval(UserInformationService.ID_LOGOUT_BUTTON);
				eventualChildNodeRemoval(UserInformationService.ID_LOGIN_BUTTON);

				profile.add(userInformationService.createLogoutButton(new Callback() {
					@Override
					public void onFailure(Object reason) {
						logger.severe("FOR SOME REASON, LOGOUT SCREWED UP.");
					}

					@Override
					public void onSuccess(Object result) {
						logger.info("SUCCESSFULLY LOGGED OUT");
						setLoggedOutState(userInformationService);
					}
				}));
			}
		};

		userInformationService.getUserInfo(clientCallBack);

	}

	private void setLoggedOutState(final UserInformationService userInformationService){
		eventualChildNodeRemoval(UserInformationService.ID_LOGIN_BUTTON);
		eventualChildNodeRemoval(UserInformationService.ID_LOGOUT_BUTTON);

		profile.getElement().removeClassName("user-logged-in");
		profile.getElement().setTitle(messages.clickToLogin());
		logger.info("WE ARE NOT LOGGED IN");
		profile.add(userInformationService.createLoginButton());

	}

	private void eventualChildNodeRemoval(String id) {
		for (int i = 0; i < profile.getWidgetCount(); i++) {
			Widget widget = profile.getWidget(i);
			if (widget instanceof ComplexWidget) {
				ComplexWidget complexWidget = (ComplexWidget) widget;
				if (id.equals(complexWidget.getId())){
					profile.remove(complexWidget);
					logger.info("REMOVED WIDGET "+id);
				}
			}
		}

	}


	private void handleCookies() {
		Collection<String> cookieNames = Cookies.getCookieNames();

		for (String cookieName : cookieNames) {
			logger.info("I found "+cookieName+" value "+Cookies.getCookie(cookieName));
		}
	}

	/**
	 * Get Widget from RootPanel
	 * @param element - name of the widget
	 * @return RootPanel
	 */
	private RootPanel getWidgets(String element) {
		return RootPanel.get(element);
	}

}
