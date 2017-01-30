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
import org.gwtbootstrap3.client.ui.*;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.constants.Placement;
import org.gwtbootstrap3.client.ui.constants.Trigger;

import java.util.logging.Logger;

public class ApplauncherWebApp implements EntryPoint {
	public static final String TMB_APP_LAUNCHER = "tmb_app_launcher";
	private static Logger logger = Logger
			.getLogger(ApplauncherWebApp.class.getName());

	private Container popupContainer;
	private Column col1;
	private Column col2;
	private Column col3;

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
		appLauncherRoot.add(dropDown);

		Popover popover = createApplauncherPopover();

		FlowPanel applauncherPanel= new FlowPanel();
		applauncherPanel.getElement().getStyle().setFloat(Style.Float.RIGHT);

		applauncherPanel.getElement().addClassName("webapps-popover-outer-div");
		applauncherPanel.getElement().setAttribute("aria-label","Interseroh-Apps");
		applauncherPanel.getElement().setAttribute("aria-hidden","false");
		applauncherPanel.getElement().setAttribute("role","region");

		createPopupContainer();
		applauncherPanel.add(popupContainer);

		popover.add(applauncherPanel);
		dropDown.add(popover);
	}

	private Container createPopupContainer(){
	    popupContainer = new Container();
		popupContainer.setFluid(true);
		popupContainer.setWidth("100%");
		popupContainer.add(createRow());
		popupContainer.add(createRow());

		return popupContainer;

	}


	private Row createRow(){
		Column col1 	= new Column("MD_4");
		Image icon = new Image("images/dienstleistung-logo.png");
		col1.add(icon);

		Column col2	= new Column("MD_4");
		Image icon2 = new Image("images/dienstleistung-logo.png");
		col2.add(icon2);
		Column col3	= new Column("MD_4");
		Image icon3 = new Image("images/dienstleistung-logo.png");
		col3.add(icon3);
		Row row = new Row();

		row.add(col1);
		row.add(col2);
		row.add(col3);
		popupContainer.add(row);
        return row;
	}


	private Popover createApplauncherPopover(){
		Popover popover = new Popover();
		popover.setTitle("Interseroh WabApps");
		popover.setIsHtml(false);
		popover.setTrigger(Trigger.CLICK);
		popover.setPlacement(Placement.BOTTOM);

		return popover;
	}
	private RootPanel getWidgets(String element) {
		RootPanel root = RootPanel.get(element);
		logger.info("getWidget:"+root);
		return root;
	}

}
