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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import de.interseroh.tmb.applauncher.client.common.ApplauncherPopover;
import de.interseroh.tmb.applauncher.client.common.ServicePreparator;
import de.interseroh.tmb.applauncher.client.domain.AppConfigurationClient;
import de.interseroh.tmb.applauncher.shared.json.TargetApplication;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.*;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.ImageType;
import org.gwtbootstrap3.client.ui.gwt.FlowPanel;

import java.util.List;
import java.util.logging.Logger;

public class ApplauncherWebApp implements EntryPoint {

	private static final String DATA_APPLICATION_URL = "data-tmb-application-url";
	private static final String TMB_APP_LAUNCHER = "tmb_app_launcher";
	private static final String CSS_BLOCK = "APL_application";

	private static final Logger logger = Logger
			.getLogger(ApplauncherWebApp.class.getName());

	private final ApplauncherWebAppGinjector injector = GWT
			.create(ApplauncherWebAppGinjector.class);

	private AppConfigurationClient appConfigurationClient;

	@Override
	public void onModuleLoad() {
		logger.info("AppLauncher: Create Views begins...");

		RootPanel appLauncherRoot = getWidgets(TMB_APP_LAUNCHER);
		appLauncherRoot.getElement().setClassName(CSS_BLOCK);
		String appUrl = appLauncherRoot.getElement()
				.getAttribute(DATA_APPLICATION_URL);
		logger.info("Applauncher application URL: " + appUrl);
		ServicePreparator servicePreparator = initServices(appUrl);

		appConfigurationClient = servicePreparator.getAppConfigurationClient();

		ListDropDown dropDown = new ListDropDown();
		dropDown.getElement().getStyle().setFloat(Style.Float.RIGHT);
		dropDown.getElement().getStyle().setMarginTop(12, Style.Unit.PX);

		Popover popover = createApplauncherPopover();

		AnchorButton popoverBtn = new AnchorButton();
		popoverBtn.setIcon(IconType.TH);
		popoverBtn.setIconSize(IconSize.TIMES3);

		createFlowPanel();

		popover.add(popoverBtn);

		createDivStructure(popover, dropDown, appLauncherRoot);

		logger.info("AppLauncher: Create Views end...");
	}

	private FlowPanel createFlowPanel() {
		return new FlowPanel();
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
		popupContainer.getElement()
				.addClassName(CSS_BLOCK + "__iconsContainer");
		popupContainer.setFluid(true);
		return popupContainer;

	}

	private void fillThreeColumnContainer(Container popupContainer,
			List<TargetApplication> webApps) {
		Row currentRow = new Row();
		popupContainer.add(currentRow);
		for (TargetApplication webApp : webApps) {
			currentRow.add(createAnchorColumn("XS_4", webApp.getCaption(),
					webApp.getApplicationURL(), webApp.getImageURL()));
		}
	}

	private Column createAnchorColumn(String span, String text, String url,
			String iconUrl) {
		Column col = new Column(span);
		Row newRow = new Row();
		SimplePanel iconWrapper = new SimplePanel();
		iconWrapper.getElement().setClassName(CSS_BLOCK + "__iconWrapper");
		Image icon = new Image(iconUrl);
		icon.setType(ImageType.CIRCLE);
		icon.setResponsive(true);
		iconWrapper.add(icon);
		Anchor anchor = new Anchor();
		anchor.setText(text);
		anchor.getElement().setClassName(CSS_BLOCK + "__link");
		anchor.setHref(url);
		newRow.add(iconWrapper);
		newRow.add(anchor);
		col.add(newRow);
		return col;
	}

	private Popover createApplauncherPopover() {
		return new ApplauncherPopover("applauncherPopover");
	}

	private RootPanel getWidgets(String element) {
		return RootPanel.get(element);
	}

	private ServicePreparator initServices(String appUrl) {
		ServicePreparator servicePreparator = injector.getServicePreparator();
		servicePreparator.prepare(appUrl);

		return servicePreparator;
	}

	private void fillApplauncherPopupPanel(FlowPanel panel, Container container,
			Popover popover, ListDropDown dropDown, RootPanel appLauncherRoot) {

		appConfigurationClient.getAppConfiguration(
				new MethodCallback<List<TargetApplication>>() {

					@Override
					public void onFailure(Method method, Throwable throwable) {
						logger.severe("Error getting applauncher properties");
					}

					@Override
					public void onSuccess(Method method,
							List<TargetApplication> appProperties) {
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
