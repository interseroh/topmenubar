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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.RootPanel;
import de.interseroh.tmb.user.client.UserInfoResponse;
import de.interseroh.tmb.user.client.UserInformationService;
import de.interseroh.tmb.user.client.UserInformationServiceImpl;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class ProfileWebApp implements EntryPoint {

	private static final String DATA_APPLICATION_URL = "data-tmb-sso-url";
	private static final String DATA_USER_INFORMATION_URL = "data-tmb-user-info";
	private static final String SSO_FALLBACK="http://localhost:9000/ep/openid_connect_login?identifier=http%3A%2F%2Flocalhost%3A8080%2Fopenid-connect-server-webapp%2F";
	private static final String USERINFO_FALLBACK="http://localhost:9000/ep/";



	private static final String TMB_PROFILE = "tmb_profile";
	private RootPanel profile;

	private static final Logger logger = Logger
			.getLogger(ProfileWebApp.class.getName());


	@Override
	public void onModuleLoad() {
		logger.info("ProfileWebApp: Create Views begins...");

		logger.info("Get Profile Widget...");
		profile = getWidgets(TMB_PROFILE);

		String ssoUrl = getSsoUrl();
		String userInfoUrl = getUserInfoUrl();

		UserInformationService userInformationService = new UserInformationServiceImpl(ssoUrl,userInfoUrl, logger);

		handleCookies();

		logger.info("Checking login state...");
		if (userInformationService.isLoggedIn()) {

			profile.getElement().addClassName("user-logged-in");
			logger.info("WE ARE LOGGED IN");

			MethodCallback<UserInfoResponse> clientCallBack = new MethodCallback<UserInfoResponse>() {
				@Override
				public void onFailure(Method method, Throwable throwable) {
					logger.severe("Retrieval of userinfo failed!");
				}

				@Override
				public void onSuccess(Method method, UserInfoResponse response) {
					Messages messages = GWT.create(Messages.class);
					logger.info("I got user "+response.getUsername()+" who is logged in.");
					profile.getElement().setTitle(messages.loggedInAs()+response.getUsername());
				}
			};

			userInformationService.getUserInfo(clientCallBack);

		} else {
			profile.getElement().removeClassName("user-logged-in");
			logger.info("WE ARE NOT LOGGED IN");
			profile.add(userInformationService.createLoginButton());
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
			logger.info("FALLING BACK TO USER INFO URL "+SSO_FALLBACK);
			userInfoUrl=SSO_FALLBACK;
		} else{
			logger.info("USING USER INFO URL "+userInfoUrl);
		}
		return userInfoUrl;
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
