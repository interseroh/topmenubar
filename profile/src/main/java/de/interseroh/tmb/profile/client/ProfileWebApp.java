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
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.ButtonType;

public class ProfileWebApp implements EntryPoint {

	public static final String SESSION_ID_COOKIE="JSESSIONID";
	public static final String USERINFO_URL_COOKIE="USERINFO";

	private static final String TMB_PROFILE = "tmb_profile";
	private RootPanel profile;

	public static final String OID_CONNECT_GATEWAY_LOCATION = "http://localhost:9000/ep/openid_connect_login?identifier=http%3A%2F%2Flocalhost%3A8080%2Fopenid-connect-server-webapp%2F";
	public static final String OID_WEBFINGER_URL = "http://localhost:9000/ep/";

	private static final Logger logger = Logger
			.getLogger(ProfileWebApp.class.getName());

	@Override
	public void onModuleLoad() {
		logger.info("ProfileWebApp: Create Views begins...");

		logger.info("Get Profile Widget...");
		profile = getWidgets(TMB_PROFILE);

		handleCookies();

		logger.info("Checking login state...");
		if (!isLoggedIn()) {
			AnchorButton loginButton = new AnchorButton(ButtonType.fromStyleName("fa-user"));

//			Element element = loginButton.getElement();
//			element.setClassName("fa-user");
//			element.addClassName("fa");
//			element.addClassName("headertabs");

			loginButton.setText("LOGIN");

			loginButton.setHref(OID_CONNECT_GATEWAY_LOCATION);
			profile.add(loginButton);
		} else {
			logger.info("WE ARE LOGGED IN");
		}




		logger.info("ProfileWebApp: Create Views ends...");
	}

	private boolean isLoggedIn(){
		boolean result=false;
		Collection<String> cookieNames = Cookies.getCookieNames();
		for (String cookieName : cookieNames) {
			if (cookieName.equals(SESSION_ID_COOKIE)) {
				result = true;
				continue;
			} else {
				logger.info("TARNATION! WRONG ONE "+cookieName);
			}
		}

		logger.info("Login flag is "+result);

		return result;
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
