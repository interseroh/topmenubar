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

import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.Navbar;
import org.gwtbootstrap3.client.ui.NavbarCollapse;
import org.gwtbootstrap3.client.ui.NavbarCollapseButton;
import org.gwtbootstrap3.client.ui.NavbarHeader;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import de.interseroh.tmb.client.common.RemoteScriptInjector;
import static de.interseroh.tmb.client.common.CopyTxtcolorToToggleNativeJavaScript.copyTxtColor;

public class TopMenuBarWebApp implements EntryPoint {

	private static final String TMB_APP_LAUNCHER = "tmb_app_launcher";
	private static final String TMB_PROFILE = "tmb_profile";
	private static final String TMB_MESSAGING = "tmb_messaging";

	private static final String ATTRIBUTE_APPLICATION_URL = "data-tmb-application-url";
	private static final String ATTRIBUTE_JAVASCRIPT_PATH = "data-tmb-javascript-url";
	private static final String DATA_TMB_BGCOLOR = "data-tmb-bgcolor";
	private static final String DATA_TMB_TXTCOLOR = "data-tmb-txtcolor";
	private static final String DATA_TMB_ICON_URL = "data-tmb-icon-url";
	private static final String DATA_TMB_HEADLINE = "data-tmb-headline";
	private static final String DATA_TMB_THEME = "data-tmb-theme";
	private static final String DEFAULT_BACKGROUND_THEME = "default";
	private static final String TOP_MENU_BAR_PLACEHOLDER = "tmb_top_menu_bar";
	private static final String TOP_MENU_ICONS_RIGHT = "icons_right";
	private static final String PORTAL_LINKS = "tmb_portal_links";
	private static final String TOPICS = "internal_topics";
	private static final String COLLAPSEID = "tmb_navbar_collapse";
	private static String CSS_BLOCK = "TMB_application";

	private static final Logger logger = Logger
			.getLogger(TopMenuBarWebApp.class.getName());

	// Create Gin Injector
	private final TopMenuBarAppGinjector injector = GWT
			.create(TopMenuBarAppGinjector.class);

	private RootPanel portal;
	private RootPanel topics;

	private RootPanel icons_right;
	private RootPanel appLauncher;
	private RootPanel profile;
	private RootPanel messaging;
	private RootPanel rootPanel;

	@Override
	public void onModuleLoad() {

		initServices();

		createViews();
	}

	private void initServices() {
		injector.getServicePreparator().prepare();
	}

	private void createViews() {
		// Views
		logger.info("Create Views begins...");

		CSS_BLOCK = loadWidgetsFromInsideOut();
		configureApplauncher();
		injectApplauncherScript();
		injectProfileScript();
		configureRootPanel();

		String bg_color = rootPanel.getElement().getAttribute(DATA_TMB_BGCOLOR);
		String text_color = rootPanel.getElement().getAttribute(DATA_TMB_TXTCOLOR);
		String theme = rootPanel.getElement().getAttribute(DATA_TMB_THEME);
		String iconUrl = rootPanel.getElement().getAttribute(DATA_TMB_ICON_URL);
		String headlineText = rootPanel.getElement().getAttribute(DATA_TMB_HEADLINE);


		Navbar basePanel = new Navbar();
		basePanel.getElement().addClassName(ifPresent(theme, DEFAULT_BACKGROUND_THEME));
		basePanel.getElement().getStyle().setBackgroundColor(bg_color);
		basePanel.getElement().getStyle().setColor(text_color);
		basePanel.getElement().getStyle().setMarginBottom(0, Style.Unit.PT);

		NavbarHeader header = new NavbarHeader();

		SimplePanel logoContainer = new SimplePanel();
		logoContainer.getElement().setClassName(CSS_BLOCK + "__logocontainer");
		logoContainer.add(createLogoImage(iconUrl));
		header.add(logoContainer);
		header.add(createTitle(headlineText));
		basePanel.add(header);

		if (topics != null) {
			topics.getElement().addClassName("nav");
			topics.getElement().addClassName("navbar-nav");
			portal.add(topics);
		}

		NavbarCollapse collapse = new NavbarCollapse();
		collapse.setId(COLLAPSEID);
		if (portal != null) {
			collapse.add(portal);
		}
		basePanel.add(collapse);

		if (icons_right != null) {
			icons_right.add(createCollapseButton("#" + COLLAPSEID));
			icons_right.add(profile);
			icons_right.add(messaging);
			icons_right.add(appLauncher);
			icons_right.getElement().addClassName("icons-right");
			basePanel.add(icons_right);
		}

		rootPanel.insert(basePanel, 0);
		copyTxtColor();
		logger.info("Create Views ends...");
	}

	private void injectApplauncherScript() {
		logger.info("Begin injection of applauncher");
		String appUrl = appLauncher.getElement().getAttribute(ATTRIBUTE_APPLICATION_URL);
		String javascriptUrl = appLauncher.getElement().getAttribute(ATTRIBUTE_JAVASCRIPT_PATH);
		new RemoteScriptInjector().injectScript(appUrl, javascriptUrl);
		logger.info("End injection of applauncher");
	}

	private void injectProfileScript() {
		logger.info("Begin injection of profile");
		String appUrl = profile.getElement().getAttribute(ATTRIBUTE_APPLICATION_URL);
		String javascriptUrl = profile.getElement().getAttribute(ATTRIBUTE_JAVASCRIPT_PATH);
		new RemoteScriptInjector().injectScript(appUrl, javascriptUrl);
		logger.info("End injection of profile");
	}

	private void configureRootPanel() {
		rootPanel.getElement().setClassName(CSS_BLOCK);
	}

	private void configureApplauncher() {
		appLauncher.getElement().addClassName("headertabs");
	}

	private String loadWidgetsFromInsideOut() {
		portal = getWidgets(PORTAL_LINKS);
		topics = getWidgets(TOPICS);
		profile = getWidgets(TMB_PROFILE);
		messaging = getWidgets(TMB_MESSAGING);
		appLauncher = getWidgets(TMB_APP_LAUNCHER);
		icons_right = getWidgets(TOP_MENU_ICONS_RIGHT);
		// Outer Widgets must be loaded at last
		rootPanel = getWidgets(TOP_MENU_BAR_PLACEHOLDER);
		return ifPresent(rootPanel.getElement().getClassName(), CSS_BLOCK);
	}

	private Widget createCollapseButton(String dataTarget) {
		NavbarCollapseButton button = new NavbarCollapseButton();
		button.getElement().addClassName(CSS_BLOCK + "__button");
		button.getElement().addClassName("headertabs");
		button.setDataTarget(dataTarget);
		return button;
	}

	/**
	 * Creates and add headline text to the left panel
	 *
	 * @param headlineText text for adding
	 */

	private SimplePanel createTitle(String headlineText) {
		SimplePanel brand = new SimplePanel();
		brand.getElement().setClassName("navbar-brand");
		brand.getElement().setInnerText(headlineText);
		return brand;
	}
	/**
	 * Creates and add Logo image to the left panel
	 *
	 * @param iconUrl the url of logo image
	 */
	private Image createLogoImage(String iconUrl) {
		return new Image(ifPresent(iconUrl, "images/broken.png"));
	}

	/**
	 * Returns value if not null or empty otherwise it returns the default value.
	 *
	 * @param value        the current value
	 * @param defaultValue the fallback default value if value is empty
	 * @return value or defaultValue
	 */
	private String ifPresent(String value, String defaultValue) {
		return value != null && !value.trim().isEmpty() ? value : defaultValue;
	}

	/**
	 * Get Widget from RootPanel
	 * @param element - name of the widget
	 * @return RootPanel
	 */
	private RootPanel getWidgets(String element) {
		return RootPanel.get(element);
	}

}
