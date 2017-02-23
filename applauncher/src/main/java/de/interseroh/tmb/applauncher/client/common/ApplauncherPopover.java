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
package de.interseroh.tmb.applauncher.client.common;

import org.gwtbootstrap3.client.ui.Popover;
import org.gwtbootstrap3.client.ui.constants.Placement;
import org.gwtbootstrap3.client.ui.constants.Trigger;

import com.google.gwt.core.client.ScriptInjector;

/**
 * This component is extension of Bootstrap Popover.
 * This component can be closed or by clicking on the Popover button or by lost of focus of Popover
 */
public class ApplauncherPopover extends Popover {
	/**
	 * The function which call for closing Popover component
	 */
	public static final String CLOSE_POPOVER_JSFUNCTION = "hideApplauncherPopover()";

	/**
	 * By default the Bootstrap generate html id dynamically. Because of that it was created alternative
	 * identification of Popover component by using data attribute 'data-topmenubar'.
	 *
	 * @param id Popover identificator.
	 */
	public ApplauncherPopover(String id) {
		ScriptInjector.fromString(createPopoverHider(id))
				.setWindow(ScriptInjector.TOP_WINDOW).inject();
		setAlternateTemplate(
				"<div class=\"popover\" style=\"max-width: 1000px;''\" role=\"tooltip\" data-topmenubar=\""
						+ id + "\">" + "<div class=\"arrow\"></div>"
						+ "<h3 class=\"popover-title\"></h3>"
						+ "<div class=\"popover-content\"></div>" + "</div>");
		setIsHtml(true);
		setTrigger(Trigger.CLICK);
		setPlacement(Placement.AUTO);

	}

	/**
	 * This method creates a java function which  closes Popover and uses for the identification
	 * of popover id parameter. The  which is defined by data attribute <code>data-topmenubar</code>
	 *
	 * @param id the identificator for Popover
	 * @return
	 */
	private String createPopoverHider(String id) {
		String jsFunction = "function " + CLOSE_POPOVER_JSFUNCTION + " {"
				+ "$('[data-topmenubar=\"" + id + "\"]').popover('hide');"
				+ " $('body').on('hidden.bs.popover', function (e) {\n"
				+ "            $(e.target).data(\"bs.popover\").inState = { click: false, hover: false, focus: false }\n"
				+ "        });" + "}";
		return jsFunction;
	}

}
