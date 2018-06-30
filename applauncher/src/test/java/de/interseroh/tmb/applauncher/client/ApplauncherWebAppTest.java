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
package de.interseroh.tmb.applauncher.client;

import com.google.gwtmockito.GwtMock;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.gwtmockito.WithClassesToStub;
import de.interseroh.tmb.applauncher.shared.json.TargetApplication;
import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Container;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
@WithClassesToStub(Anchor.class)
public class ApplauncherWebAppTest {

	@Spy
	private ApplauncherWebApp applauncherWebApp;

	@GwtMock
	private ApplauncherWebAppGinjector injector;

	@GwtMock
	private Container popupContainer;

	private List<TargetApplication> webApps = new ArrayList<>();

	@GwtMock
	private TargetApplication targetApplication;

	@GwtMock
	private Column column;

	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	@Test
	public void testOnModuleLoad() throws Exception {
		applauncherWebApp.onModuleLoad();
	}

	@Test
	public void fillThreeColumnContainer() throws Exception {
		// Prepare
		webApps.add(targetApplication);

		when(targetApplication.getCaption()).thenReturn("Test");
		when(targetApplication.getApplicationURL()).thenReturn("Test");
		when(targetApplication.getImageURL()).thenReturn("Test");
		when(targetApplication.getTarget()).thenReturn("Test");

		doReturn(column).when(applauncherWebApp).createAnchorColumn(
				"XS_4", "Test", "Test",
				"Test", "test");

		// CUT
		applauncherWebApp.fillThreeColumnContainer(popupContainer, webApps);

		// Asserts
		verify(popupContainer, times(1)).add(any());
		verify(webApps.get(0), times(1)).getCaption();
	}
}