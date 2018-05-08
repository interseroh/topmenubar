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
package de.interseroh.tmb.user.client;

import java.util.logging.Logger;

import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.base.ComplexWidget;
import org.gwtbootstrap3.client.ui.constants.ButtonType;

import com.google.gwt.core.client.Callback;
import com.google.gwt.user.client.Cookies;

/**
 * Mock User Service.
 */
public class UserInformationServiceImpl implements UserInformationService {
	public final static String USER_LOGIN = "userLogin";

	private static final Logger logger = Logger
			.getLogger(UserInformationServiceImpl.class.getName());

	public UserInformationServiceImpl(String gatewayLocation,
			String ssoLogoutUrl, String userInfoUrl, String cookiePath,
			String logoutUrl) {
		String AND = "\n AND ";
		logger.info("USER INFORMATION MOCK HAS BEEN CONFIGURED WITH " + (
				gatewayLocation == null || gatewayLocation.trim().isEmpty() ?
						" NO GATEWAY " :
						gatewayLocation) + AND + (
				ssoLogoutUrl == null || ssoLogoutUrl.trim().isEmpty() ?
						" NO SSO LOGOUTHOOK " :
						gatewayLocation) + AND + (
				userInfoUrl == null || userInfoUrl.trim().isEmpty() ?
						" NO USER INFO URL " :
						userInfoUrl) + AND + (
				cookiePath == null || cookiePath.trim().isEmpty() ?
						" NO" :
						cookiePath) + " COOKIE PATH" + AND + (
				logoutUrl == null || logoutUrl.trim().isEmpty() ?
						" NO" :
						logoutUrl) + " LOGOUT URL");
	}

	/**
	 * create a dummy login button and set the JSESSIONID cookie. this cookie must be
	 * deleted manually if the isLoggedIn logic shall be retested.
	 *
	 * @return a login button
	 */
	@Override
	public ComplexWidget createLoginButton() {
		AnchorButton loginButton = new AnchorButton(
				ButtonType.fromStyleName("fa-user"));
		loginButton.setHref("./index.html");
		loginButton.getElement().addClassName(USER_LOGIN);
		loginButton.setId(ID_LOGIN_BUTTON);

		Cookies.setCookie("JSESSIONID", "http://www.dilbert.com");

		return loginButton;
	}

	@Override
	public ComplexWidget createLogoutButton(Callback logoutCallback) {
		AnchorButton loginButton = new AnchorButton(
				ButtonType.fromStyleName("fa-user"));
		loginButton.setHref("./index.html");
		loginButton.getElement().addClassName(USER_LOGIN);
		loginButton.setId(ID_LOGOUT_BUTTON);

		loginButton.addClickHandler(event -> performLogout(logoutCallback));

		return loginButton;
	}

	/**
	 * @return a dummy user
	 */
	@Override
	public void getUserInfo(MethodCallback<UserInfoResponse> uiCallback) {
		uiCallback.onSuccess(null, new UserInfoResponse() {

			@Override
			public String getUsername() {
				return "Hein Blöd";
			}

			@Override
			public void setUsername(String username) {
				// Nothing to do
			}

			@Override
			public String getEmail() {
				return "hein.bloed@zamonien.com";
			}

			@Override
			public void setEmail(String email) {
				// Nothing to do
			}
		});
	}

}
