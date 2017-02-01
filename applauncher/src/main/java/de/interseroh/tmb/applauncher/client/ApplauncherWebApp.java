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
package de.interseroh.tmb.applauncher.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Widget;
import de.interseroh.tmb.applauncher.shared.TargetedApplication;
import org.gwtbootstrap3.client.ui.*;
import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.constants.*;
import org.gwtbootstrap3.client.ui.gwt.FlowPanel;
import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.UnorderedList;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ApplauncherWebApp implements EntryPoint {
	public static final String TMB_APP_LAUNCHER = "tmb_app_launcher";
	private static Logger logger = Logger
			.getLogger(ApplauncherWebApp.class.getName());

	private Column col1;
	private Column col2;
	private Column col3;


	static List<TargetedApplication> applications = new ArrayList<>();

	static {
		applications.add(new TargetedApplication("Entsorger","http://www.google.de", "images/entsorger-logo.png"));
		applications.add(new TargetedApplication("Aufträge","http://www.google.de", "images/auftrags-logo.png"));
		applications.add(new TargetedApplication("eco24","http://www.google.de", "images/ecoservice24-logo.png"));
		applications.add(new TargetedApplication("isupplier","http://www.google.de", "images/isupplier-logo.png"));
		applications.add(new TargetedApplication("Mengen","http://www.google.de", "images/mengenmeldung-logo.png"));
		applications.add(new TargetedApplication("Drache","http://www.google.de", "images/sammeldrache-logo.jpeg"));
		applications.add(new TargetedApplication("Dienste","http://www.google.de", "images/dienstleistung-logo.png"));
	}

	@Override
	public void onModuleLoad() {
		logger.info("AppLauncher: Create Views begins...");

		GWT.log("Hello Applaucher!", null);

		logger.info("AppLauncher: Create Views end...");
//		ErrorFormatter errorFormater = new ErrorFormatter();
//		GWT.runAsync(new RunAsyncCallback() {
//			@Override
//			public void onFailure(Throwable reason) {
//				errorFormater.showError(reason,"Error ApplauncherPanelView");
//			}
//
//			@Override
//			public void onSuccess() {
//				ApplauncherPanelView view = new ApplauncherPanelView();
//				RootPanel.get().add(view);
//			}
//		});



		RootPanel appLauncherRoot = getWidgets(TMB_APP_LAUNCHER);
		ListDropDown dropDown = new ListDropDown();
		dropDown.getElement().getStyle().setFloat(Style.Float.RIGHT);
		dropDown.setMarginRight(200);

		Popover popover = createApplauncherPopover();
		popover.setAlternateTemplate("<div class=\"popover\" style=\"max-width: 1000px;''\" role=\"tooltip\"><div class=\"arrow\"></div><h3 class=\"popover-title\"></h3><div class=\"popover-content\"></div></div>");

		AnchorButton popoverBtn= new AnchorButton();
		popoverBtn.setIcon(IconType.TH);
		popoverBtn.setIconSize(IconSize.LARGE);

		createFlowPanel();

		popover.add(popoverBtn);
		popover.setContent(createDivStructure().getElement().getString());
		dropDown.add(popover);

		appLauncherRoot.add(dropDown);
	}

	private FlowPanel createFlowPanel() {
		FlowPanel applauncherPanel= new FlowPanel();

		return applauncherPanel;
	}

	private Widget createDivStructure() {
		FlowPanel panel = createFlowPanel();
		Container container = createPopupContainer();
		fillThreeColumnContainer(container, applications);
		panel.add(container);
	return container;
	}

	private Container createPopupContainer(){
		Container popupContainer = new Container();
		popupContainer.setFluid(true);
		popupContainer.setWidth("200px");
		return popupContainer;

	}


	private void fillThreeColumnContainer(Container popupContainer, List<TargetedApplication> webApps){
		int actCol = 0;
		Row currentRow=null;
		for (TargetedApplication webApp : webApps) {
			if (actCol == 0) {
				 currentRow = new Row();
				popupContainer.add(currentRow);
			}
			currentRow.add(createAnchorColumn("MD_4",webApp.getName(), webApp.getUrl(), webApp.getIconUrl()));
			actCol++;
			if (actCol >= 3) {
				actCol = 0;
			}
		}
	}


	public Column createAnchorColumn(String span, String text, String url, String iconUrl) {
		Column col = new Column(span);
		col.getElement().getStyle().setVerticalAlign(Style.VerticalAlign.MIDDLE);

		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		panel.setWidth("100%");
		Image icon = new Image(iconUrl);
		icon.setWidth("25px");
		icon.setHeight("25px");
		icon.setType(ImageType.CIRCLE);
		icon.setResponsive(true);
		icon.getElement().addClassName("glyphicon");

		Anchor anchor = new Anchor();
		anchor.setText(text);
		anchor.setHref(url);
		panel.add(icon);
		panel.add(anchor);
		col.add(panel);
		return col;
	}



	private Popover createApplauncherPopover(){
		Popover popover = new Popover();

		popover.setTitle("Interseroh WabApps");
		popover.setIsHtml(true);
		popover.setTrigger(Trigger.CLICK);
		popover.setPlacement(Placement.AUTO);

		return popover;
	}
	private RootPanel getWidgets(String element) {
		RootPanel root = RootPanel.get(element);
		logger.info("getWidget:"+root);
		return root;
	}



}
