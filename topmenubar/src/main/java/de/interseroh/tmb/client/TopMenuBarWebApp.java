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
import com.google.gwt.user.client.ui.*;
import de.interseroh.tmb.client.common.ServicePreparator;
import org.gwtbootstrap3.client.ui.*;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.gwt.FlowPanel;

public class TopMenuBarWebApp implements EntryPoint {

	private static Logger logger = Logger
			.getLogger(TopMenuBarWebApp.class.getName());

	public static final String TMB_APP_LAUNCHER = "tmb_app_launcher";
	public static final String TMB_PROFILE = "tmb_profile";
	public static final String TMB_MESSAGING = "tmb_messaging";
	private static final String TOP_MENU_BAR_PLACEHOLDER = "tmb_top_menu_bar";
	private Row mainPanel;
	private Column leftPanel;
	private Column rightPanel;



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

	private void createViews() {
		// Views
		logger.info("Create Views begins...");


		RootPanel rootPanel = getWidgets(TOP_MENU_BAR_PLACEHOLDER);

		String colour = rootPanel.getElement().getAttribute("data-colour");
		String iconUrl = rootPanel.getElement().getAttribute("data-icon-url");
		String geadlineText = rootPanel.getElement().getAttribute("data-headline");

		FlowPanel basePanel  = new FlowPanel();
		basePanel.getElement().getStyle().setBackgroundColor(colour != null && !colour.trim().isEmpty() ? colour : "#FF0000");

		Container container = createMD6Container();
		createAndAddLogoImage(iconUrl);
		createAndAddHeadlineText(geadlineText);
		moveElementsToRightPanel();

		basePanel.add(container);
		rootPanel.insert(basePanel,0);
		logger.info("Create Views ends...");
	}

	/**
	 * add elements to the right panel
	 */
	private void moveElementsToRightPanel(){
		RootPanel appLauncher = getWidgets(TMB_APP_LAUNCHER);
		RootPanel profile = getWidgets(TMB_PROFILE);
		RootPanel messaging = getWidgets(TMB_MESSAGING);
		appLauncher.removeFromParent();
		profile.removeFromParent();
		messaging.removeFromParent();
		rightPanel.add(messaging);
		rightPanel.add(profile);
		rightPanel.add(appLauncher);

	}

	/**
	 * Creates and add headline text to the left panel
	 * @param geadlineText text for adding
	 */
	private void createAndAddHeadlineText(String geadlineText){
		Label headline = new Label(geadlineText != null && !geadlineText.isEmpty() ? geadlineText : "NO HEADLINE!");
		headline.getElement().addClassName("tmb_headline_text_cls");
		leftPanel.add(headline);
	}

	/**
	 * Creates and add Logo image to the left panel
	 * @param iconUrl the url of logo image
	 */
	private void createAndAddLogoImage(String iconUrl){
		Image icon = new Image(() -> (iconUrl != null && !iconUrl.trim().isEmpty() ? iconUrl : "images/broken.png"));
		icon.getElement().addClassName("tmb_logo_icon_cls");
		leftPanel.add(icon);
	}

	/**
	 * Creates Container with two panels left and right with size MD_6
	 * @return
	 */
	private Container createMD6Container(){
		Container container = new Container();
		container.setFluid(true);
		container.setWidth("100%");
		mainPanel = new Row();

		leftPanel = new Column("MD_6");
		rightPanel = new Column("MD_6");
		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);

		container.add(mainPanel);
		return container;
	}



	private RootPanel getWidgets(String element) {
		return RootPanel.get(element);
	}



}
