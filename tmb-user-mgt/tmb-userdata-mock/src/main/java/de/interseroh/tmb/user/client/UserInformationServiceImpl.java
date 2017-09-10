package de.interseroh.tmb.user.client;

import com.google.gwt.user.client.Cookies;
import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.base.ComplexWidget;
import org.gwtbootstrap3.client.ui.constants.ButtonType;


/**
 * Mock User Service
 */
public class UserInformationServiceImpl implements UserInformationService{


    /**
     * create a dummy login button and set the JSESSIONID cookie. this cookie must be
     * deleted manually if the isLoggedIn logic shall be retested.
     *
     * @return a login button
     */
    @Override
    public ComplexWidget createLoginButton() {
        AnchorButton loginButton = new AnchorButton(ButtonType.fromStyleName("fa-user"));
        loginButton.setText("LOGIN");
        loginButton.setHref("./index.html");

        Cookies.setCookie("JSESSIONID","http://www.dilbert.com");

        return loginButton;
    }

    /**
     * @return a dummy user
     */
    @Override
    public UserInfoResponse getUserInfo() {
        return new UserInfoResponse("Hein Blöd","hein.bloed@blaubaer.com");
    }


}
