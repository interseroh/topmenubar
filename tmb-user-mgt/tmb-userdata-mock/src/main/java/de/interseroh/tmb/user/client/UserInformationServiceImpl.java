package de.interseroh.tmb.user.client;

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


    public UserInformationServiceImpl(String gatewayLocation, String userInfoUrl, Logger logger) {
        logger.info("USER INFORMATION MOCK HAS BEEN CONFIGURED WITH "
                +(gatewayLocation==null || gatewayLocation.trim().isEmpty()? " NO GATEWAY ":gatewayLocation)
                + " AND "
                +(userInfoUrl == null || userInfoUrl.trim().isEmpty() ? " NO USER INFO URL ": userInfoUrl));
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

        Cookies.setCookie("JSESSIONID","http://www.dilbert.com");

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

            }

            @Override
            public void setEmail(String email) {

            }
        });
    }


}
