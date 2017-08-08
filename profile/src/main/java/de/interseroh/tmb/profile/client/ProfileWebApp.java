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
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import de.interseroh.tmb.profile.client.common.ProfilePopover;
import org.gwtbootstrap3.client.ui.*;
import org.gwtbootstrap3.client.ui.constants.AlertType;
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



	private static final Logger logger = Logger
			.getLogger(ProfileWebApp.class.getName());

	private final ProfileWebAppGinjector injector = GWT
			.create(ProfileWebAppGinjector.class);

	@Override
	public void onModuleLoad() {
		logger.info("ProfileWebApp: Create Views begins...");

		RootPanel profileRoot = getWidgets(TMB_PROFILE);
		profileRoot.getElement().addClassName(CSS_BLOCK);

		ListDropDown dropDown = new ListDropDown();
		dropDown.getElement().getStyle().setFloat(Style.Float.RIGHT);

		Popover popover = new ProfilePopover("profilePopover");

		AnchorButton popoverBtn = new AnchorButton();
		popoverBtn.setIcon(IconType.USER);
		popoverBtn.setIconSize(IconSize.TIMES3);

		popover.add(popoverBtn);

		FlowPanel panel = createFlowPanel();
		Container container = createPopupContainer();

		panel.add(container);

		Div divClose = new Div();
		divClose.addStyleName(getStyle("close"));
		divClose.getElement().setInnerText("x");
		String closeDivStr = divClose.getElement().getString();
		String containerStr = container.getElement().getString();
		popover.setContent(closeDivStr + containerStr);

		dropDown.add(popover);
		dropDown.getElement().setAttribute("onFocusOut", "javascript:"+ProfilePopover.CLOSE_POPOVER_JSFUNCTION+";");
		profileRoot.add(dropDown);

		logger.info("ProfileWebApp: Create Views end");
	}

	private RootPanel getWidgets(String element) {
		return RootPanel.get(element);
	}

	private FlowPanel createFlowPanel() {
		return new FlowPanel();
	}

	private Container createPopupContainer() {
		Container popupContainer = new Container();
		popupContainer.getElement()
				.addClassName(CSS_BLOCK + "__iconsContainer");
		popupContainer.setFluid(true);
		createLoginForm(popupContainer);
		return popupContainer;

	}

	private void createLoginForm(Container popupContainer) {
		String IdTxtBenutzerName = "IdTxtBenutzerName";
		String labelText = "Benutzername";
		Form logingForm = new Form();
		logingForm.getElement().setId("loginform");
		logingForm.getElement().setAttribute(ProfilePopover.NON_CLOCEABLE_POPOVER,"true");
		//create group user name
		FormGroup groupUserName = new FormGroup();
		groupUserName.getElement().setAttribute(ProfilePopover.NON_CLOCEABLE_POPOVER,"true");
		FormLabel labelUserName = new FormLabel();
		labelUserName.setFor(IdTxtBenutzerName);
		labelUserName.setText(labelText);
		labelUserName.getElement().setAttribute(ProfilePopover.NON_CLOCEABLE_POPOVER,"true");
		TextBox txtUserName = new TextBox();
		txtUserName.setId(IdTxtBenutzerName);
		txtUserName.setPlaceholder(labelText);
		txtUserName.getElement().setAttribute(ProfilePopover.NON_CLOCEABLE_POPOVER,"true");
		groupUserName.add(labelUserName);
		groupUserName.add(txtUserName);
		logingForm.add(groupUserName);
		/////
		createPassordFormGroup(logingForm);

		createButtonsGroup(logingForm);

		PanelBody panelBody = new PanelBody();
		panelBody.add(createAlert("Ihre Anmeldedaten waren nicht richtig.",
				"wrong_incendetials","glyphicon-exclamation-sign"));

		panelBody.add(createAlert(
				"Bitte füllen sie die rot markierten Felder aus.",
				"empty_alert","empty_alert"));

		panelBody.add(logingForm);
//        Column col= new Column("MD_12");
//		col.add(panelBody);
//		popupContainer.add(col);
		popupContainer.add(panelBody);
	}

	private Alert createAlert(String msg, String style, String iconStyle) {
		Alert alert = new Alert();
		alert.setType(AlertType.DANGER);
		alert.addStyleName(style);
		Span glypImg = new Span();
        glypImg.addStyleName("glyphicon");
		glypImg.addStyleName(iconStyle);
		alert.add(glypImg);
		Div txt = new Div();
		txt.getElement().setInnerText(msg);
		alert.getElement().appendChild(txt.getElement().getFirstChild());
		alert.setVisible(false);

		return alert;
	}

	private void createPassordFormGroup(Form logingForm) {
		String IdTxtPassword = "IdTxtPassword";
		String labelText = "Passwort";

		FormGroup groupPassword = new FormGroup();
		groupPassword.getElement().setAttribute(ProfilePopover.NON_CLOCEABLE_POPOVER,"");
		FormLabel labelPassword = new FormLabel();
		labelPassword.setFor(IdTxtPassword);
		labelPassword.setText(labelText);
		labelPassword.getElement().setAttribute(ProfilePopover.NON_CLOCEABLE_POPOVER,"");
		TextBox txtPassword = new TextBox();
		txtPassword.getElement().setAttribute("type", "password");
		txtPassword.getElement().setAttribute("autocomplete", "false");
		txtPassword.setId(IdTxtPassword);
		txtPassword.setPlaceholder(labelText);
		txtPassword.getElement().setAttribute(ProfilePopover.NON_CLOCEABLE_POPOVER,"");
		groupPassword.add(labelPassword);
		groupPassword.add(txtPassword);

		logingForm.add(groupPassword);
	}

	private void createButtonsGroup(Form logingForm) {
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.addStyleName("pull-right");
		Button btnCancel = new Button();
		btnCancel.setType(ButtonType.DEFAULT);
		btnCancel.getElement().setAttribute("type", "reset");
		btnCancel.setText("Abbrechen");
		btnCancel.getElement().setAttribute(ProfilePopover.NON_CLOCEABLE_POPOVER,"");

		Button btnSubmit = new Button();
		btnSubmit.setType(ButtonType.PRIMARY);
		btnSubmit.getElement().setAttribute("type", "submit");

		Span glypImg = new Span();
		glypImg.addStyleName("glyphicon");
		glypImg.addStyleName(getStyle("glyphicon-log-in"));
		glypImg.getElement().setAttribute(ProfilePopover.NON_CLOCEABLE_POPOVER,"");
		btnSubmit.add(glypImg);
		Div txtDiv = new Div();
		txtDiv.getElement().setInnerText("  Anmelden");
		btnSubmit.getElement().appendChild(txtDiv.getElement().getFirstChild());
		btnSubmit.getElement().setAttribute(ProfilePopover.NON_CLOCEABLE_POPOVER,"");
		buttonGroup.add(btnCancel);
		buttonGroup.add(btnSubmit);
		logingForm.add(buttonGroup);
	}

	private String getStyle(String style) {
		return style;
	}

}
