package de.interseroh.tmb.user.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.user.client.Cookies;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.base.ComplexWidget;

import java.util.Collection;
import java.util.logging.Logger;

/**
 * Abstracting the user related login and  user information services in order to enable a development-mode mocking
 *
 */
public interface UserInformationService {

    String SESSION_ID_COOKIE="JSESSIONID";
    String ID_LOGIN_BUTTON="TMB_PRF_LOGINBUTTON";
    String ID_LOGOUT_BUTTON="TMB_PRF_LOGOUTBUTTON";

    /**
     * @return true, if the user is logged id
     */
    default boolean isLoggedIn(){
        boolean result=false;
        Collection<String> cookieNames = Cookies.getCookieNames();
        for (String cookieName : cookieNames) {
            if (cookieName.equals(SESSION_ID_COOKIE)) {
                result = true;
                continue;
            }
        }
        return result;
    }

    /**
     * @return true, if the session cookie has been removed
     */
    default boolean performLogout(Callback logoutCallback) {
        boolean result = false;
        if(containsSessionIdCookie()) {
            String cookiePath = getCookiePath();
            if ( cookiePath != null) {
                Cookies.removeCookie(SESSION_ID_COOKIE, cookiePath);
            } else {
                Cookies.removeCookie(SESSION_ID_COOKIE);
            }
            if (containsSessionIdCookie()){
                Logger logger = Logger
                        .getLogger(this.getClass().getName());
                logger.warning("WARNING! I FOUND "+SESSION_ID_COOKIE+" BUT IT I WAS UNABLE TO DELETE IT! ARE YOU SURE, THAT YOU HAVE SET THE CORRECT COOKIE PATH ("+cookiePath+") TO MATCH THE COOKIE?");
            } // double check that it is really gone!
            result = true;
        }

        return result;
    }

    default boolean containsSessionIdCookie() {
        Collection<String> cookieNames = Cookies.getCookieNames();
        return cookieNames.contains(SESSION_ID_COOKIE);
    }

    /**
     * @return a preconfigured widget which contains everything needed to perform a login
     */
    ComplexWidget createLoginButton();


    /**
     * @param logoutCallback - ui behaviour on logout
     * @return the logout button
     */
    ComplexWidget createLogoutButton(Callback logoutCallback);

    /**
     * @return the user information or null if the user is not logged in
     */
    void getUserInfo(MethodCallback<UserInfoResponse> uiRepsonse);

    default String getCookiePath() {
        return null;
    }
}
