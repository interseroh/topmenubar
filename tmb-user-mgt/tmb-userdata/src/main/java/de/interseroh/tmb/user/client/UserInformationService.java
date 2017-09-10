package de.interseroh.tmb.user.client;

import com.google.gwt.user.client.Cookies;
import org.gwtbootstrap3.client.ui.base.ComplexWidget;

import java.util.Collection;

/**
 * Abstracting the user related login and  user information services in order to enable a development-mode mocking
 *
 */
public interface UserInformationService {

    String SESSION_ID_COOKIE="JSESSIONID";
    String USERINFO_URL_COOKIE="USERINFO";

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
     * @return a preconfigured widget which contains everything needed to perform a login
     */
    ComplexWidget createLoginButton();


    /**
     * @return the user information or null if the user is not logged in
     */
    UserInfoResponse getUserInfo();

}
