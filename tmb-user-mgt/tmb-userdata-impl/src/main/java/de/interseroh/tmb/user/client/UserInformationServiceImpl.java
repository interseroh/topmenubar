package de.interseroh.tmb.user.client;

import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.base.ComplexWidget;
import org.gwtbootstrap3.client.ui.constants.ButtonType;

public class UserInformationServiceImpl implements UserInformationService {

    public final String OID_CONNECT_GATEWAY_LOCATION;

    public UserInformationServiceImpl(String gatewayLocation) {
        OID_CONNECT_GATEWAY_LOCATION=gatewayLocation;
    }

    @Override
    public ComplexWidget createLoginButton() {
        AnchorButton loginButton = new AnchorButton(ButtonType.fromStyleName("fa-user"));

        loginButton.getElement().addClassName("userLogin");
        loginButton.setHref(OID_CONNECT_GATEWAY_LOCATION);

        return loginButton;
    }

    @Override
    public UserInfoResponse getUserInfo() {
        return null;
    }


}
