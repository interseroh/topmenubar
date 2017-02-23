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

import java.util.List;
import java.util.logging.Logger;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.ListDropDown;
import org.gwtbootstrap3.client.ui.Popover;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.ImageType;
import org.gwtbootstrap3.client.ui.gwt.FlowPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.interseroh.tmb.applauncher.client.common.ApplauncherPopover;
import de.interseroh.tmb.applauncher.client.common.ServicePreparator;
import de.interseroh.tmb.applauncher.client.domain.AppConfigurationClient;
import de.interseroh.tmb.applauncher.shared.json.TargetedApplication;

public class ApplauncherWebApp implements EntryPoint {

	public static final String DATA_APPLICATION_URL = "data-tmb-application-url";
	public static final String TMB_APP_LAUNCHER = "tmb_app_launcher";

	private static final Logger logger = Logger
			.getLogger(ApplauncherWebApp.class.getName());
	private final ApplanucherWebAppGinjector injector = GWT
			.create(ApplanucherWebAppGinjector.class);
	private AppConfigurationClient appConfigurationClient;

	@Override
	public void onModuleLoad() {
		logger.info("AppLauncher: Create Views begins...");

		RootPanel appLauncherRoot = getWidgets(TMB_APP_LAUNCHER);
		String appUrl = appLauncherRoot.getElement()
				.getAttribute(DATA_APPLICATION_URL);
		logger.info("++++++++++++++++++++Applauncher application URL:"+appUrl);
		ServicePreparator servicePreparator = initServices(appUrl);

		appConfigurationClient = servicePreparator.getAppConfigurationClient();

		ListDropDown dropDown = new ListDropDown();
		dropDown.getElement().getStyle().setFloat(Style.Float.RIGHT);

		Popover popover = createApplauncherPopover();

		AnchorButton popoverBtn = new AnchorButton();
		popoverBtn.setIcon(IconType.TH);
		popoverBtn.setIconSize(IconSize.LARGE);

		createFlowPanel();

		popover.add(popoverBtn);

		createDivStructure(popover, dropDown, appLauncherRoot);

		logger.info("AppLauncher: Create Views end...");
	}

	private FlowPanel createFlowPanel() {
		FlowPanel applauncherPanel = new FlowPanel();

		return applauncherPanel;
	}

	private void createDivStructure(Popover popover, ListDropDown dropDown,
			RootPanel appLauncherRoot) {
		FlowPanel panel = createFlowPanel();
		Container container = createPopupContainer();
		fillApplauncherPopupPanel(panel, container, popover, dropDown,
				appLauncherRoot);
	}

	private Container createPopupContainer() {
		Container popupContainer = new Container();
		popupContainer.getElement().addClassName("applauncherContainerCls");
		popupContainer.setFluid(true);
		return popupContainer;

	}

	private void fillThreeColumnContainer(Container popupContainer,
			List<TargetedApplication> webApps) {

		int actCol = 0;
		Row currentRow = null;
		for (TargetedApplication webApp : webApps) {
			if (actCol == 0) {
				currentRow = new Row();
				popupContainer.add(currentRow);
			}
			currentRow.add(createAnchorColumn("MD_4", webApp.getCaption(),
					webApp.getApplicationURL(), webApp.getImageURL()));
			actCol++;
			if (actCol >= 3) {
				actCol = 0;
			}
		}
	}

	private Column createAnchorColumn(String span, String text, String url,
			String iconUrl) {
		Column col = new Column(span);
		col.getElement().getStyle()
				.setVerticalAlign(Style.VerticalAlign.MIDDLE);

		VerticalPanel panel = new VerticalPanel();
		panel.getElement().addClassName("applauncherVerticalBar");
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		Image icon = new Image(iconUrl);
		icon.getElement().addClassName("applauncherIconCls");
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

	private Popover createApplauncherPopover() {
		Popover popover = new ApplauncherPopover("Id241");
		return popover;
	}

	private RootPanel getWidgets(String element) {
		RootPanel root = RootPanel.get(element);
		return root;
	}

	private ServicePreparator initServices(String appUrl) {
		ServicePreparator servicePreparator = injector.getServicePreparator();
		servicePreparator.prepare(appUrl);

		return servicePreparator;
	}

	private void fillApplauncherPopupPanel(FlowPanel panel, Container container,
			Popover popover, ListDropDown dropDown, RootPanel appLauncherRoot) {

		appConfigurationClient.getAppConfiguration(
				new MethodCallback<List<TargetedApplication>>() {

					@Override
					public void onFailure(Method method, Throwable throwable) {
						logger.severe("Error getting applauncher properties");
					}

					@Override
					public void onSuccess(Method method,
							List<TargetedApplication> appProperties) {
						fillThreeColumnContainer(container, appProperties);
						panel.add(container);
						popover.setContent(container.getElement().getString());
						dropDown.add(popover);
						appLauncherRoot.add(dropDown);
						dropDown.getElement().setAttribute("onFocusOut",
								"javasript:"
										+ ApplauncherPopover.CLOSE_POPOVER_JSFUNCTION
										+ ";");
					}
				});
	}

}
