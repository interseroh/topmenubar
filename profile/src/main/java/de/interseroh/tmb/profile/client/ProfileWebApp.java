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
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.RootPanel;
import de.interseroh.tmb.user.client.UserInfoResponse;
import de.interseroh.tmb.user.client.UserInformationService;
import de.interseroh.tmb.user.client.UserInformationServiceImpl;
import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.constants.ButtonType;

public class ProfileWebApp implements EntryPoint {


	private static final String TMB_PROFILE = "tmb_profile";
	private RootPanel profile;

	private static final Logger logger = Logger
			.getLogger(ProfileWebApp.class.getName());

	private final UserInformationService userInformationService = new UserInformationServiceImpl();

	@Override
	public void onModuleLoad() {
		logger.info("ProfileWebApp: Create Views begins...");

		logger.info("Get Profile Widget...");
		profile = getWidgets(TMB_PROFILE);

		handleCookies();

		logger.info("Checking login state...");
		if (!userInformationService.isLoggedIn()) {
			logger.info("WE ARE NOT LOGGED IN");
			profile.add(userInformationService.createLoginButton());
		} else {
			logger.info("WE ARE LOGGED IN");
			UserInfoResponse userInfoResponse = userInformationService.getUserInfo();

			// TODO : use this information and display it somehow

		}




		logger.info("ProfileWebApp: Create Views ends...");
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
