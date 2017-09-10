package de.interseroh.tmb.user.client;

import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.base.ComplexWidget;
import org.gwtbootstrap3.client.ui.constants.ButtonType;

public class UserInformationServiceImpl implements UserInformationService {

    public static final String OID_CONNECT_GATEWAY_LOCATION = "http://localhost:9000/ep/openid_connect_login?identifier=http%3A%2F%2Flocalhost%3A8080%2Fopenid-connect-server-webapp%2F";

    @Override
    public ComplexWidget createLoginButton() {
        AnchorButton loginButton = new AnchorButton(ButtonType.fromStyleName("fa-user"));

        loginButton.setText("LOGIN");

        loginButton.setHref(OID_CONNECT_GATEWAY_LOCATION);

        return loginButton;
    }

    @Override
    public UserInfoResponse getUserInfo() {
        return null;
    }


}
