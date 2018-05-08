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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.base.ComplexWidget;
import org.gwtbootstrap3.client.ui.constants.ButtonType;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

import de.interseroh.tmb.user.client.domain.UserInfoClient;

public class UserInformationServiceImpl implements UserInformationService {

	public final static String USER_CLASS = "userLogin";
	private static final Logger logger = Logger
			.getLogger(UserInformationServiceImpl.class.getName());
	public final String oidConnectGatewayLocation;
	public final String userInfoUrl;
	public final String cookiePath;
	public final String logoutUrl;
	public final String ssoLogoutUrl;

	public UserInformationServiceImpl(String gatewayLocation,
			String ssoLogoutUrl, String userInfoUrl, String cookiePath,
			String logoutUrl) {
		oidConnectGatewayLocation = gatewayLocation;
		this.ssoLogoutUrl = ssoLogoutUrl;
		this.userInfoUrl = userInfoUrl;
		this.cookiePath = cookiePath;
		this.logoutUrl = logoutUrl;
	}

	public UserInformationServiceImpl(UserInformationServiceImpl dolly) {
		this.oidConnectGatewayLocation = dolly.oidConnectGatewayLocation;
		this.userInfoUrl = dolly.userInfoUrl;
		this.cookiePath = dolly.cookiePath;
		this.logoutUrl = dolly.logoutUrl;
		this.ssoLogoutUrl = dolly.ssoLogoutUrl;
	}

	@Override
	public ComplexWidget createLoginButton() {
		AnchorButton loginButton = new AnchorButton(
				ButtonType.fromStyleName("fa-user"));
		loginButton.getElement().addClassName(USER_CLASS);
		loginButton.setHref(oidConnectGatewayLocation);
		loginButton.setId(ID_LOGIN_BUTTON);

		return loginButton;
	}

	@Override
	public ComplexWidget createLogoutButton(final Callback logoutCallback) {
		final UserInformationServiceImpl me = this;
		AnchorButton logoutButton = new AnchorButton(
				ButtonType.fromStyleName("fa-user"));
		logoutButton.getElement().addClassName(USER_CLASS);
		logoutButton.setId(ID_LOGOUT_BUTTON);

		final UserInformationServiceImpl userService = new UserInformationServiceImpl(
				me);
		logoutButton.addClickHandler(clickEvent -> {
			userService.logger.info("BEGIN INITIATING PERFORM LOGOUT");
			userService.performLogout(logoutCallback);
			userService.logger.info("DONE INITIATING PERFORM LOGOUT");
		});

		return logoutButton;
	}

	@Override
	public boolean performLogout(final Callback logoutCallback) {
		logger.info("BEGIN PERFOMING LOGOUT");

		try {
			// In order to destory all session info, we are stacking the callbacks like a matryoshka.
			// last step : terminate session and navigate to logout url
			final LogoutTask methodObject3 = new LogoutTask(
					new UserInformationServiceImpl(this)) {
				@Override
				public void run() {
					logger.info("BEGIN COOKIE REMOVAL");
					LogoutCallback callback = new LogoutCallback(
							new UserInformationServiceImpl(userService),
							logoutCallback) {
						@Override
						public void onFailure(Object reason) {
							userService.logger
									.info("A FAILURE HAS HAPPENED. REASON: " + (
											reason != null ?
													reason.toString() :
													"NULL"));
							logoutCallback.onFailure(reason);
						}

						@Override
						public void onSuccess(Object result) {
							logger.info("Cookie removed successful.");
						}
					};

					userService.removeCookies(callback);
					logger.info("END COOKIE REMOVAL");

					if (logoutUrl != null && !logoutUrl.trim().isEmpty()) {
						if (ssoLogoutUrl != null && !ssoLogoutUrl.isEmpty()) {
							String logoutHook = ssoLogoutUrl
									+ "/endsession?post_logout_redirect_uri="
									+ logoutUrl;
							userService.logger.info("MOVING TO " + logoutHook);
							Window.Location.replace(logoutHook);
						} else {
							userService.logger.info("MOVING TO " + logoutUrl);
							Window.Location.replace(logoutUrl);
						}
					}
					logoutCallback.onSuccess(null);
				}
			};

			// second step : end gateway session
			final LogoutTask methodObject2 = new LogoutTask(
					new UserInformationServiceImpl(this)) {
				@Override
				public void run() {
					logger.info("BEGIN GW SHALLOW LOGOUT");
					final UserInfoClient userInfoClient = userService
							.getUserInfoClient();
					userInfoClient.logout(new MethodCallback<Void>() {
						@Override
						public void onFailure(Method method,
								Throwable exception) {
							logger.warning("GW LOGOUT FAILED " + exception
									.getMessage());
							methodObject3.run();
						}

						@Override
						public void onSuccess(Method method, Void response) {
							logger.info("GW LOGOUT SUCCESSFUL");
							methodObject3.run();
						}
					});
					logger.info("DONE GW SHALLOW LOGOUT");
				}
			};

			// first step : end sso session
			final LogoutTask methodObject1 = new LogoutTask(
					new UserInformationServiceImpl(this)) {
				@Override
				public void run() {
					logger.info("BEGIN DEEP LOGOUT");
					logger.log(Level.INFO,
							() -> "Deep Logout : logoutURL = " + logoutUrl);
					final UserInfoClient userInfoClient = userService
							.getUserInfoClient();
					logger.log(Level.INFO,
							() -> "UserInfoClient: LoggedIn?" + userService
									.isLoggedIn());
					userInfoClient
							.deepLogout(logoutUrl, new MethodCallback<Void>() {
								@Override
								public void onFailure(Method method,
										Throwable exception) {
									logger.warning(
											"DEEP LOGOUT FAILED " + exception
													.getMessage());
									methodObject2.run();
								}

								@Override
								public void onSuccess(Method method,
										Void response) {
									logger.info("DEEP LOGOUT SUCCESSFUL");
									methodObject2.run();
								}
							});
					logger.info("DONE DEEP LOGOUT");
				}
			};

			logger.info("LAUNCHING: PERFORM LOGOUT");
			// go!
			methodObject1.run();
			logger.fine("DONE: PERFOMING LOGOUT");

		} catch (Exception e) {
			logger.warning("SOMETHING SCREWED UP ON LOGOUT :" + e.getMessage());
			return false;
		}

		return true;
	}

	@Override
	public void getUserInfo(MethodCallback<UserInfoResponse> uiCallback) {
		logger.info("Get userInfo begins...");

		UserInfoClient client = getUserInfoClient();

		MethodCallback<UserInfoResponseImpl> callback = new MethodCallback<UserInfoResponseImpl>() {
			@Override
			public void onFailure(Method method, Throwable exception) {
				logger.severe("FAILED TO RETRIEVE USER DATA. ERROR " + exception
						.getClass().getName() + " MSG " + exception
						.getMessage());
				uiCallback.onFailure(method, exception);
			}

			@Override
			public void onSuccess(Method method,
					UserInfoResponseImpl response) {
				uiCallback.onSuccess(method, response);
			}
		};
		client.getUserInfo(callback);

		logger.info("Get userInfo ends...");
	}

	/**
	 * @return a instance of the user info client
	 */
	private UserInfoClient getUserInfoClient() {
		Resource resource = new Resource(userInfoUrl);

		UserInfoClient client = GWT.create(UserInfoClient.class);
		((RestServiceProxy) client).setResource(resource);
		return client;
	}

	@Override
	public String getCookiePath() {
		return cookiePath;
	}

	void removeCookies(final Callback callback) {
		if (UserInformationService.super.performLogout(callback)) {
			logger.fine("SESSION COOKIE SUCCESSFULLY REMOVED");
		} else {
			logger.warning("NO SESSION COOKIE FOUND FOR REMOVAL?");
		}
	}

	private static abstract class LogoutTask implements Runnable {

		final UserInformationServiceImpl userService;

		private LogoutTask(UserInformationServiceImpl userService) {
			this.userService = userService;
		}

		@Override
		public abstract void run();
	}

	private static abstract class LogoutCallback implements Callback {

		final UserInformationServiceImpl userService;
		final Callback nestedCallback;

		private LogoutCallback(UserInformationServiceImpl userService,
				Callback nestedCallback) {
			this.userService = userService;
			this.nestedCallback = nestedCallback;
		}
	}
}
