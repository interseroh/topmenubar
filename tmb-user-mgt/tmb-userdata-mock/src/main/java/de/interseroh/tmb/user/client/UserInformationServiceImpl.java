package de.interseroh.tmb.user.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.base.ComplexWidget;
import org.gwtbootstrap3.client.ui.constants.ButtonType;

import java.util.logging.Logger;


/**
 * Mock User Service
 */
public class UserInformationServiceImpl implements UserInformationService{

    private static final Logger logger = Logger
            .getLogger(UserInformationServiceImpl.class.getName());

    public UserInformationServiceImpl(String gatewayLocation, String userInfoUrl, String cookiePath, String logoutUrl) {
        String AND = "\n AND ";
        logger.info("USER INFORMATION MOCK HAS BEEN CONFIGURED WITH "
                +(gatewayLocation==null || gatewayLocation.trim().isEmpty()? " NO GATEWAY ":gatewayLocation)
                + AND
                +(userInfoUrl == null || userInfoUrl.trim().isEmpty() ? " NO USER INFO URL ": userInfoUrl)
                + AND
                + (cookiePath == null || cookiePath.trim().isEmpty()? " NO" : cookiePath)+" COOKIE PATH"
                + AND
                + (logoutUrl == null || logoutUrl.trim().isEmpty()? " NO" : logoutUrl)+" LOGOUT URL");
    }

    /**
     * create a dummy login button and set the JSESSIONID cookie. this cookie must be
     * deleted manually if the isLoggedIn logic shall be retested.
     *
     * @return a login button
     */
    @Override
    public ComplexWidget createLoginButton() {
        AnchorButton loginButton = new AnchorButton(ButtonType.fromStyleName("fa-user"));
        loginButton.setHref("./index.html");
        loginButton.getElement().addClassName("userLogin");
        loginButton.setId(ID_LOGIN_BUTTON);

        Cookies.setCookie("JSESSIONID","http://www.dilbert.com");

        return loginButton;
    }

    @Override
    public ComplexWidget createLogoutButton(Callback logoutCallback) {
        AnchorButton loginButton = new AnchorButton(ButtonType.fromStyleName("fa-user"));
        loginButton.setHref("./index.html");
        loginButton.getElement().addClassName("userLogin");
        loginButton.setId(ID_LOGOUT_BUTTON);

        loginButton.addClickHandler(event -> performLogout());

        return loginButton;
    }

    /**
     * @return a dummy user
     */
    @Override
    public void getUserInfo(MethodCallback<UserInfoResponse> uiCallback) {
        uiCallback.onSuccess(null,new UserInfoResponse(){

            @Override
            public String getUsername() {
                return "Hein Blöd";
            }

            @Override
            public String getEmail() {
                return "hein.bloed@zamonien.com";
            }

            @Override
            public void setUsername(String username) {
                // Nothing to do
            }

            @Override
            public void setEmail(String email) {
                // Nothing to do
            }
        });
    }


}
