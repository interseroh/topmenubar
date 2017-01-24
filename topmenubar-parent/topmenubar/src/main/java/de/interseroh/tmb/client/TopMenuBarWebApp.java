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
package de.interseroh.tmb.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.*;
import de.interseroh.tmb.client.common.ServicePreparator;
import org.gwtbootstrap3.client.ui.*;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.base.HasInlineStyle;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.gwt.FlowPanel;

public class TopMenuBarWebApp implements EntryPoint {

	private static Logger logger = Logger
			.getLogger(TopMenuBarWebApp.class.getName());

	public static final String TMB_APP_LAUNCHER = "tmb_app_launcher";
	public static final String TMB_PROFILE = "tmb_profile";
	public static final String TMB_MESSAGING = "tmb_messaging";
	private static final String TOP_MENU_BAR_PLACEHOLDER = "tmb_top_menu_bar";


	// Create Gin Injector
	private final TopMenuBarAppGinjector injector = GWT
			.create(TopMenuBarAppGinjector.class);

	@Override
	public void onModuleLoad() {

		initServices();

		createViews();

	}
//	private void removeLoadingImage() {
//		// Remove loadingImage from Host HTML page
//		RootPanel rootPanel = RootPanel.get(HOST_LOADING_IMAGE);
//		if (rootPanel != null) {
//			RootPanel.getBodyElement().removeChild(rootPanel.getElement());
//		}
//	}

	private void initServices() {
		ServicePreparator servicePreparator = injector.getServicePreparator();
		servicePreparator.prepare();
	}


	private void createViews2() {
		// Views
		logger.info("Create Views begins...");

		RootPanel appLauncher = getWidgets(TMB_APP_LAUNCHER);
		RootPanel profile = getWidgets(TMB_PROFILE);
		RootPanel messaging = getWidgets(TMB_MESSAGING);
		RootPanel rootPanel = getWidgets(TOP_MENU_BAR_PLACEHOLDER);

		FlowPanel panel = new FlowPanel();

		FlowPanel left 	= new FlowPanel();
		FlowPanel right = new FlowPanel();

		handleLeftSide(left);

		handleRightElement(appLauncher, rootPanel, right);

		handleRightElement(profile, rootPanel, right);

		handleRightElement(messaging, rootPanel, right);

		handleRightElement(new Label("Blalblabla"), rootPanel, right);

		stylePanel(right);

		panel.add(left);
		panel.add(right);


		rootPanel.insert(panel,0);

		logger.info("Create Views ends...");
	}


	private void createViews() {
		// Views
		logger.info("Create Views begins...");

		RootPanel appLauncher = getWidgets(TMB_APP_LAUNCHER);
		RootPanel profile = getWidgets(TMB_PROFILE);
		RootPanel messaging = getWidgets(TMB_MESSAGING);
		RootPanel rootPanel = getWidgets(TOP_MENU_BAR_PLACEHOLDER);

		FlowPanel f  = new FlowPanel();
		f.getElement().getStyle().setBackgroundColor("#223d62");

		Container container = new Container();
		container.setFluid(true);
		container.setWidth("100%");

		Row row = new Row();

		Column left 	= new Column("MD_6");
		Column right	= new Column("MD_6");

		Image icon = new Image(() -> "images/entsorger-logo.png");
		Label headline = new Label("TEST APPLICATION");
		floatLeft(icon);
		floatLeft(headline);
		margin10(icon);
		margin10(headline);

		left.add(icon);
		left.add(headline);

		handlePanelElement(appLauncher, rootPanel, right);

		handlePanelElement(profile, rootPanel, right);

		handlePanelElement(messaging, rootPanel, right);

		Label blalblabla = new Label("Blalblabla");
		floatRight(blalblabla);
		margin10(blalblabla);
		right.add(blalblabla);

		row.add(left);
		row.add(right);

		container.add(row);

		f.add(container);

		rootPanel.insert(f,0);

		logger.info("Create Views ends...");
	}

	private void handlePanelElement(RootPanel appLauncher, RootPanel rootPanel, Column right) {
		if (appLauncher != null) {
			margin10(appLauncher);
			rootPanel.remove(appLauncher);
			floatRight(appLauncher);
			right.add(appLauncher);
		}
	}

	private void handleLeftSide(FlowPanel left) {
		Image icon = new Image(() -> "images/entsorger-logo.png");
		Label headline = new Label("TEST APPLICATION");
		floatLeft(icon);
		floatLeft(headline);
		left.add(icon);
		left.add(headline);
		stylePanel(left);
	}

	private void handleRightElement(Widget appLauncher, RootPanel rootPanel, FlowPanel right) {
		if (appLauncher != null) {
			rootPanel.remove(appLauncher);
			floatRight(appLauncher);
			right.add(appLauncher);
		}
	}



	private void stylePanel(FlowPanel left) {
//		left.getElement().getStyle().setPadding(10, Style.Unit.PT);
		left.getElement().getStyle().setBackgroundColor("#223d62");
	}

	private void margin10(UIObject icon) {
		icon.getElement().getStyle().setMargin(10, Style.Unit.PT);
	}


	private void floatLeft(UIObject icon) {
		icon.getElement().getStyle().setFloat(Style.Float.LEFT);
	}

	private void floatRight(UIObject icon) {
		icon.getElement().getStyle().setFloat(Style.Float.RIGHT);
	}

	private RootPanel getWidgets(String element) {
		return RootPanel.get(element);
	}

}
