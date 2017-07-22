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
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.RootPanel;
import de.interseroh.tmb.profile.client.common.ProfilePopover;
import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.ListDropDown;
import org.gwtbootstrap3.client.ui.Popover;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.gwt.FlowPanel;

import java.util.logging.Logger;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
public class ProfileWebApp implements EntryPoint {

    private static final String TMB_PROFILE = "tmb_profile";

    private static final String CSS_BLOCK = "PROFILE_application";

    private static final Logger logger = Logger
            .getLogger(ProfileWebApp.class.getName());

    private final ProfileWebAppGinjector injector =
            GWT.create(ProfileWebAppGinjector.class);

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
        popover.setContent(container.getElement().getString());
        dropDown.add(popover);
        dropDown.getElement().setAttribute("onFocuesOut", "javascript:"+ProfilePopover.CLOSE_POPOVER_JSFUNCTION+";");
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
        return popupContainer;

    }

}
