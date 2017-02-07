package de.interseroh.tmb.client.common;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.user.client.Window;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Injects JavaScript  from remote server
 */
public class RemoteScriptInjector {
	private static final Logger logger =
			Logger.getLogger(RemoteScriptInjector.class.getName());


	public void injectScript(String applicationUrl, String scriptPath){
		String scriptFullUrl=applicationUrl+scriptPath;
		logger.info("Start JavaScript injecting  from URL :"+scriptFullUrl);
		ScriptInjector.fromUrl(scriptFullUrl).setCallback(
				new Callback() {
					@Override
					public void onFailure(Object o) {

						logger.info("Error: Can not load JavaScript from from URL :"+scriptFullUrl);
					}

					@Override
					public void onSuccess(Object o) {
						logger.info("Java Script is lodaed from URL :"+scriptFullUrl);
					}
				}).setWindow(ScriptInjector.TOP_WINDOW).inject();
	}

}
