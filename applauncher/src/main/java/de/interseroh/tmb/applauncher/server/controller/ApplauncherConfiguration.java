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
package de.interseroh.tmb.applauncher.server.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.interseroh.tmb.applauncher.shared.ApplauncherServiceEndpoint;
import de.interseroh.tmb.applauncher.shared.json.ApplauncherProperties;
import de.interseroh.tmb.applauncher.shared.json.TargetedApplication;

@RestController
@CrossOrigin
public class ApplauncherConfiguration {
	private static final Logger logger = LoggerFactory
			.getLogger(ApplauncherConfiguration.class);

	@Value("${applauncher.config.json:classpath:dev.applauncher.json}")
	private Resource applauncherConfigurationJson;

	@RequestMapping(value = ApplauncherServiceEndpoint.APPLAUNCHER_CONFIG, method = RequestMethod.GET)
	public List<TargetedApplication> getConfiguration() {
		List<TargetedApplication> listAppProps = new ArrayList<>();
		try (InputStream jsonIs = applauncherConfigurationJson
				.getInputStream()) {
			ObjectMapper mapper = new ObjectMapper();
			ApplauncherProperties appPropes = mapper
					.readValue(jsonIs, ApplauncherProperties.class);
			listAppProps.addAll(appPropes.getApplauncherProperties()
					.getTargetedApplication());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return listAppProps;
	}
}
