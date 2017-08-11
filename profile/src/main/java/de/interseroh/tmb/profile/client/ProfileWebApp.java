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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.RootPanel;
import de.interseroh.tmb.profile.client.common.ProfilePopover;
import org.gwtbootstrap3.client.ui.*;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.gwt.FlowPanel;
import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import java.util.logging.Logger;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
public class ProfileWebApp implements EntryPoint {

	private static final String TMB_PROFILE = "tmb_profile";

	private static final String CSS_BLOCK = "PROFILE_application";
	public static final String LOGIN_LOCATION = "http://localhost:8090/openid_connect_login?identifier=http%3A%2F%2Flocalhost%3A8080%2Fopenid-connect-server-webapp%2F";
	public static final String OID_CONNECT_LOCATION = "http://localhost:9012/profile/openid_connect_login?identifier=http%3A%2F%2Flocalhost%3A8080%2Fopenid-connect-server-webapp%2F";

	private static String callee = "";
	private static String calleeUrl = "";


	private static final Logger logger = Logger
			.getLogger(ProfileWebApp.class.getName());
	public static final String OPENID_CONNECT_LOGIN = "openid_connect_login";
	public static final String OID_SERVER_LOGIN = "http://localhost:8080/openid-connect-server-webapp/";

	private final ProfileWebAppGinjector injector = GWT
			.create(ProfileWebAppGinjector.class);

	@Override
	public void onModuleLoad() {
		logger.info("ProfileWebApp: Create Views begins...");

		RootPanel profileRoot = getWidgets(TMB_PROFILE);
		profileRoot.getElement().addClassName(CSS_BLOCK);

		AnchorButton loginButton = new AnchorButton();
		loginButton.setHref(OID_CONNECT_LOCATION);
		loginButton.setText("Anmelden");
		profileRoot.add(loginButton);

		callee = com.google.gwt.user.client.Window.Location.getParameter("callee");
		calleeUrl = com.google.gwt.user.client.Window.Location.getParameter("calleeUrl");

		logger.info("Referencing App is "+callee+" located at "+calleeUrl);
/*		ListDropDown dropDown = new ListDropDown();
		dropDown.getElement().getStyle().setFloat(Style.Float.RIGHT);

		AnchorButton popoverBtn = new AnchorButton();
		popoverBtn.setIcon(IconType.USER);
		popoverBtn.setIconSize(IconSize.TIMES3);
*/

		// profileRoot.add(createLoginForm());


/*		FlowPanel panel = createFlowPanel();
		Container container = createPopupContainer();

		panel.add(container);

		Div divClose = new Div();
		divClose.addStyleName(getStyle("close"));
		divClose.getElement().setInnerText("x");
		String closeDivStr = divClose.getElement().getString();
		String containerStr = container.getElement().getString();
		popover.setContent(closeDivStr + containerStr);
		dropDown.add(popover);
//		dropDown.getElement().setAttribute("onFocusOut", "javascript:"+ProfilePopover.CLOSE_POPOVER_JSFUNCTION+";");
		profileRoot.add(dropDown);

*/

		logger.info("ProfileWebApp: Create Views end");
	}

	private boolean isLoggedIn() {
		return false;
	}

	private RootPanel getWidgets(String element) {
		return RootPanel.get(element);
	}

	private String getStyle(String style) {
		return style;
	}



	private PanelBody createLoginForm() {
		Form logingForm = new Form();
		logingForm.getElement().setId("loginform");
		logingForm.setAction(LOGIN_LOCATION);

		SubmitButton btnSubmit = new SubmitButton();
		Div txtDiv = new Div();
		txtDiv.getElement().setInnerText("Anmelden");
		btnSubmit.getElement().appendChild(txtDiv.getElement().getFirstChild());
		logingForm.add(btnSubmit);

		Hidden identifier = new Hidden();
		identifier.setID("identifier");
		identifier.setDefaultValue(OID_SERVER_LOGIN);

		PanelBody panelBody = new PanelBody();
		panelBody.add(logingForm);
		return panelBody;
	}

}
