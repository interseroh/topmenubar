package de.interseroh.tmb.client.common;

/**
 * Created by volker on 06.05.2017.
 */
public class CopyTxtcolorToToggleNativeJavaScript {
	/**
	 * This method copies the textcolor of the parent to the togglebutton
	 *
	 * Without calling this method it could be possible to inject javascript code
	 * into your main page.
	 */
	public static native void copyTxtColor() /*-{
        $wnd.jQuery('#tmb_top_menu_bar .navbar-toggle .icon-bar').css('background-color', $wnd.jQuery('.navbar').css('color'));
        $wnd.jQuery('#tmb_profile').css('border-color',  $wnd.jQuery('.navbar').css('color'));
    }-*/;
}
