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
import com.google.gwt.user.client.ui.*;
import de.interseroh.tmb.applauncher.client.ui.ApplauncherPanelView;
import org.gwtbootstrap3.client.ui.*;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.constants.Placement;
import org.gwtbootstrap3.client.ui.constants.Trigger;
import org.gwtbootstrap3.client.ui.gwt.CellTable;

import java.util.logging.Logger;

public class ApplauncherWebApp implements EntryPoint {
	public static final String TMB_APP_LAUNCHER = "tmb_app_launcher";
	private static Logger logger = Logger
			.getLogger(ApplauncherWebApp.class.getName());

	private Container container;
	private Column col1;
	private Column col2;
	private Column col3;

	@Override
	public void onModuleLoad() {
		logger.info("AppLauncher: Create Views begins...");

		GWT.log("Hello Applaucher!", null);

		logger.info("AppLauncher: Create Views end...");

		RootPanel appLauncherRoot = getWidgets(TMB_APP_LAUNCHER);
		ListDropDown dropDown = new ListDropDown();
		appLauncherRoot.add(dropDown);

		Popover popover = createApplauncherPopover();
		dropDown.add(popover);



		FlowPanel applauncherPanel= new FlowPanel();

		applauncherPanel.getElement().addClassName("webapps-popover-outer-div");
		applauncherPanel.getElement().setAttribute("aria-label","Interseroh-Apps");
		applauncherPanel.getElement().setAttribute("aria-hidden","false");
		applauncherPanel.getElement().setAttribute("role","region");
		applauncherPanel.add(createPopupContainer());

		popover.add(applauncherPanel);
	}

	private Container createPopupContainer(){

	   Container container = new Container();
		container.setFluid(true);
		container.setWidth("100%");
		container.add(createRow());
		container.add(createRow());

		return container;

	}


	private Row createRow(){
		Column col1 	= new Column("MD_4");
		Image icon = new Image("images/dienstleistung-logo.png");
		col1.add(icon);

		Column col2	= new Column("MD_4");
		col2.add(icon);
		Column col3	= new Column("MD_4");
		col3.add(icon);
		Row row = new Row();
		container.add(row);
		row.add(col1);
		row.add(col2);
		row.add(col3);
		container.add(row);
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
