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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import de.interseroh.tmb.client.common.ServicePreparator;
import org.gwtbootstrap3.client.ui.*;
import org.gwtbootstrap3.client.ui.constants.BadgePosition;
import org.gwtbootstrap3.client.ui.constants.Pull;

import java.util.logging.Logger;

public class TopMenuBarWebApp implements EntryPoint {

	public static final String TMB_APP_LAUNCHER = "tmb_app_launcher";
	public static final String TMB_PROFILE = "tmb_profile";
	public static final String TMB_MESSAGING = "tmb_messaging";
	private static final String TOP_MENU_BAR_PLACEHOLDER = "tmb_top_menu_bar";
    private static Logger logger = Logger
            .getLogger(TopMenuBarWebApp.class.getName());
    // Create Gin Injector
    private final TopMenuBarAppGinjector injector = GWT
            .create(TopMenuBarAppGinjector.class);
	RootPanel rootPanel;
	RootPanel appLauncher;
	RootPanel profile;
	RootPanel messaging;

	@Override
	public void onModuleLoad() {

		initServices();

		createViews();

	}

	private void initServices() {
		ServicePreparator servicePreparator = injector.getServicePreparator();
		servicePreparator.prepare();
	}

	private void createViews() {
		// Views
		logger.info("Create Views begins...");

		appLauncher = getWidgets(TMB_APP_LAUNCHER);
		profile = getWidgets(TMB_PROFILE);
		messaging = getWidgets(TMB_MESSAGING);

		rootPanel = getWidgets(TOP_MENU_BAR_PLACEHOLDER);

		String colour = rootPanel.getElement().getAttribute("data-colour");
		String iconUrl = rootPanel.getElement().getAttribute("data-icon-url");
		String headlineText = rootPanel.getElement().getAttribute("data-headline");

		Navbar basePanel = new Navbar();
		// FlowPanel basePanel  = new FlowPanel();
		basePanel.getElement().getStyle().setBackgroundColor(colour != null && !colour.trim().isEmpty() ? colour : "#FF0000");

		NavbarHeader header = new NavbarHeader();
		header.add(createLogoImage(iconUrl));
		header.add(createBadge(headlineText, iconUrl));
		header.add(createCollapseButton("tmb_navbar_collapse"));
		basePanel.add(header);

		NavbarCollapse collapse = new NavbarCollapse();
		collapse.setId("tmb_navbar_collapse");
		collapse.add(rightPanelElements());

		rightPanelElements(collapse);

		basePanel.add(collapse);

		//Container container = createMD6Container();
		//createAndAddLogoImage(iconUrl);
		//createAndAddHeadlineText(geadlineText);
		//moveElementsToRightPanel();

		//basePanel.add(container);
		rootPanel.insert(basePanel,0);
		logger.info("Create Views ends...");
	}

	private Widget createCollapseButton(String tmb_navbar_collapse) {
		NavbarCollapseButton button = new NavbarCollapseButton();
		button.setDataTarget(tmb_navbar_collapse);
		return button;
	}

	/**
	 * add elements to the right panel
	 */
	private NavbarNav rightPanelElements(){

		appLauncher.removeFromParent();
		profile.removeFromParent();
		messaging.removeFromParent();

		NavbarNav nav = new NavbarNav();
		nav.setPull(Pull.RIGHT);

		nav.add(messaging);
		nav.add(profile);
		nav.add(appLauncher);

		return nav;
	}

	/**
	 * add elements to the right panel
	 */
	private void rightPanelElements(NavbarCollapse parent){

		appLauncher.removeFromParent();
		profile.removeFromParent();
		messaging.removeFromParent();

		parent.add(messaging);
		parent.add(profile);
		parent.add(appLauncher);

	}

	/**
	 * Creates and add headline text to the left panel
	 * @param headlineText text for adding
	 */
	private NavbarBrand createBadge(String headlineText, String iconUrl){
		NavbarBrand brand = new NavbarBrand();
		brand.setBadgePosition(BadgePosition.RIGHT);
		brand.setBadgeText(headlineText);
		return brand;
	}

	/**
	 * Creates and add Logo image to the left panel
	 * @param iconUrl the url of logo image
	 */
	private Image createLogoImage(String iconUrl){
		Image icon = new Image(() -> (iconUrl != null && !iconUrl.trim().isEmpty() ? iconUrl : "images/broken.png"));
		icon.setPull(Pull.LEFT);
		return icon;
	}

	private RootPanel getWidgets(String element) {
		return RootPanel.get(element);
	}

}
