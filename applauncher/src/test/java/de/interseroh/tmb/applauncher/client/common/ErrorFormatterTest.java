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

package de.interseroh.tmb.applauncher.client.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.rule.OutputCapture;

import com.google.gwtmockito.GwtMockitoTestRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@RunWith(GwtMockitoTestRunner.class)
public class ErrorFormatterTest {

	private static Logger log = Logger
			.getLogger(ErrorFormatter.class.getName());
	private static OutputStream logCapturingStream;
	private static StreamHandler customLogHandler;

	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@BeforeClass
	public static void attachLogCapturer() {
		logCapturingStream = new ByteArrayOutputStream();
		Handler[] handlers = log.getParent().getHandlers();
		customLogHandler = new StreamHandler(logCapturingStream,
				handlers[0].getFormatter());
		log.addHandler(customLogHandler);
	}

	@Test(expected = NullPointerException.class)
	public void testWithNullValues() throws Exception {
		new ErrorFormatter().showError(null, null);
	}

	@Test
	public void testWithNoInputMessage() throws Exception {
		new ErrorFormatter().showError(new Exception(), null);
	}

	@Test
	public void testWithInputMessage() throws Exception {
		new ErrorFormatter()
				.showError(new Exception(), "InputMessage for Test");

		outputCapture.flush();
		assertThat(getTestCapturedLog(),
				containsString("InputMessage for Test"));
	}

	public String getTestCapturedLog() throws IOException {
		customLogHandler.flush();
		return logCapturingStream.toString();
	}
}