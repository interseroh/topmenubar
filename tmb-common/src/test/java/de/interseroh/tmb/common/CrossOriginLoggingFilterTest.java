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

package de.interseroh.tmb.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootApplication
public class CrossOriginLoggingFilterTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testFilter() throws Exception {
		ResponseEntity<String> response = restTemplate
				.exchange(CommonServiceEndpoint.LOGGING_CONTEXTPATH,
						HttpMethod.GET, null, String.class);

		HttpHeaders headers = response.getHeaders();

		assertThat(headers.get("Access-Control-Allow-Origin"), contains("*"));
		assertThat(headers.get("Access-Control-Allow-Methods"),
				contains("POST, TRACE, GET, UPDATE, OPTIONS"));
		assertThat(headers.get("Access-Control-Allow-Headers"), contains(
				"content-type, x-gwt-module-base, x-gwt-permutation,"
						+ " Origin, X-Requested-With, Content-Type, "
						+ "Accept, X-HTTP-Method-Override"));

	}
}