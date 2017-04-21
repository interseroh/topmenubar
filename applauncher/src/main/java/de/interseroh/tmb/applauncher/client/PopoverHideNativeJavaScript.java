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

public class PopoverHideNativeJavaScript {
	/**
	 * This method solves a security problem when external links are opened in a new tab.
	 * This method must be called in your {@link com.google.gwt.core.client.EntryPoint}
	 * in method {@link com.google.gwt.core.client.EntryPoint#onModuleLoad}.
	 *
	 * Without calling this method it could be possible to inject javascript code
	 * into your main page.
	 *
	 * For details see https://mathiasbynens.github.io/rel-noopener/
	 */
	public static native void noopenerImitate() /*-{
        $wnd.jQuery($doc).on('click', 'a[target=_blank]', function (event) {
            var href = $wnd.jQuery(this).attr('href');
            if (navigator.userAgent.indexOf("Safari") && (navigator.userAgent.indexOf("Chrome") === -1)) {
                iframeOpen(href);
                return false;
            }
            var e = window.open();
            return e.opener = null, e.location = href, false;
        });

        function iframeOpen(url) {
            var iframe, iframeDoc, script, newWin;

            iframe = document.createElement('iframe');
            iframe.style.display = 'none';
            document.body.appendChild(iframe);
            iframeDoc = iframe.contentDocument || iframe.contentWindow.document;

            script = iframeDoc.createElement('script');
            script.type = 'text/javascript';
            script.text = 'window.parent = null; window.top = null;' +
                'window.frameElement = null; var child = window.open("' + url + '");' +
                'child.opener = null';
            iframeDoc.body.appendChild(script);
            newWin = iframe.contentWindow.child;

            document.body.removeChild(iframe);
            return newWin;
        }
    }-*/;
}
