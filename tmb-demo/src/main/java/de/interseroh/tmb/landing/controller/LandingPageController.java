/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one
 *  * or more contributor license agreements.  See the NOTICE file
 *  * distributed with this work for additional information
 *  * regarding copyright ownership.  The ASF licenses this file
 *  * to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance
 *  * with the License.  You may obtain a copy of the License at
 *  *
 *  *  http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an
 *  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  * KIND, either express or implied.  See the License for the
 *  * specific language governing permissions and limitations
 *  * under the License.
 *
 */
package de.interseroh.tmb.landing.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LandingPageController {

	@Value("${applauncher.url:http://localhost:9014/applauncher}")
	private String applauncherUrl;

	@Value("${topmenubar.url:http://localhost:9010/topmenubar}")
	private String topmenubarUrl;

	@Value("${profile.url:http://localhost:9012/profile}")
	private String profileUrl;

	@Value("${sso.url:http://localhost:9000/ep/openid_connect_login?identifier=http%3A%2F%2Flocalhost%3A8080%2Fopenid-connect-server-webapp%2F}")
	private String ssoUrl;

	@Value("${sso.url:http://localhost:8080/openid-connect-server-webapp/endsession")
	private String ssoLogoutUrl;

	@Value("${userInfoUrl.url:http://localhost:9000/ep/}")
	private String userInfoUrl;

	@Value("${cookiePath.url:/ep}")
	private String cookiePath;

	@Value("${logout.url:http://www.google.de}")
	private String logoutUrl;

	@GetMapping(path = "{viewname}.html")
	public String doGet(Model model,
			@PathVariable("viewname") String viewname) {
		model.addAttribute("applauncherUrl", applauncherUrl);
		model.addAttribute("topmenubarUrl", topmenubarUrl);
		model.addAttribute("profileUrl", profileUrl);
		model.addAttribute("ssoUrl", ssoUrl);
		model.addAttribute("userInfoUrl", userInfoUrl);
		model.addAttribute("cookiePath", cookiePath);
		model.addAttribute("logoutUrl", logoutUrl);
		model.addAttribute("ssoLogoutUrl", ssoLogoutUrl);
		return viewname;
	}

}
