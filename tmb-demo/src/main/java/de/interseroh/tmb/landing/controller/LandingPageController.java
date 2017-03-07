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

	@GetMapping(path = "{viewname}.html")
	public String doGet(Model model,
			@PathVariable("viewname") String viewname) {
		model.addAttribute("applauncherUrl", applauncherUrl);
		model.addAttribute("topmenubarUrl", topmenubarUrl);
		return viewname;
	}

}
