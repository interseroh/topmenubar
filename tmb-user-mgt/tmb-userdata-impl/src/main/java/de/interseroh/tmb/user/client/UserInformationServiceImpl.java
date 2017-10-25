package de.interseroh.tmb.user.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import de.interseroh.tmb.user.client.domain.UserInfoClient;
import org.fusesource.restygwt.client.*;
import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.base.ComplexWidget;
import org.gwtbootstrap3.client.ui.constants.ButtonType;

import java.util.logging.Logger;

public class UserInformationServiceImpl implements UserInformationService {

    public final String OID_CONNECT_GATEWAY_LOCATION;
    public final String USER_INFO_URL;
    public final String COOKIE_PATH;
    public final String LOGOUT_URL;

    private static final Logger logger = Logger
            .getLogger(UserInformationServiceImpl.class.getName());

    public UserInformationServiceImpl(String gatewayLocation, String userInfoUrl, String cookiePath, String logoutUrl) {
        OID_CONNECT_GATEWAY_LOCATION=gatewayLocation;
        USER_INFO_URL = userInfoUrl;
        COOKIE_PATH = cookiePath;
        LOGOUT_URL = logoutUrl;
    }

    public UserInformationServiceImpl(UserInformationServiceImpl dolly){
        this.OID_CONNECT_GATEWAY_LOCATION = dolly.OID_CONNECT_GATEWAY_LOCATION;
        this.USER_INFO_URL = dolly.USER_INFO_URL;
        this.COOKIE_PATH = dolly.COOKIE_PATH;
        this.LOGOUT_URL = dolly.LOGOUT_URL;
    }

    @Override
    public ComplexWidget createLoginButton() {
        AnchorButton loginButton = new AnchorButton(ButtonType.fromStyleName("fa-user"));
        loginButton.getElement().addClassName("userLogin");
        loginButton.setHref(OID_CONNECT_GATEWAY_LOCATION);
        loginButton.setId(ID_LOGIN_BUTTON);

        return loginButton;
    }

    @Override
    public ComplexWidget createLogoutButton(final Callback logoutCallback) {
        final UserInformationServiceImpl me = this;
        AnchorButton logoutButton =  new AnchorButton(ButtonType.fromStyleName("fa-user"));
        logoutButton.getElement().addClassName("userLogin");
        logoutButton.setId(ID_LOGOUT_BUTTON);
        logoutButton.addClickHandler(new ClickHandler() {
            final UserInformationServiceImpl userService = new UserInformationServiceImpl(me);
            @Override
            public void onClick(ClickEvent event) {
                try {
                    userService.logger.info("PERFORM LOGOUT");
                    userService.performLogout();
                    logoutCallback.onSuccess(null);
                    if (LOGOUT_URL != null && !LOGOUT_URL.trim().isEmpty()) {
                        userService.logger.info("MOVING TO "+LOGOUT_URL);
                        Window.Location.replace(LOGOUT_URL);
                    }
                } catch (Exception e){
                    logoutCallback.onFailure(e);
                }

            }
        });
        return logoutButton;
    }

    public boolean performLogout() {
        logger.fine("REMOVING SESSION COOKIE");
        if (UserInformationService.super.performLogout()){
            logger.fine("SESSION COOKIE SUCCESSFULLY REMOVED");
        } else {
            logger.warning("NO SESSION COOKIE FOUND FOR REMOVAL?");
        }
        try {
            getUserInfoClient().logout(new MethodCallback<Void>() {
                @Override
                public void onFailure(Method method, Throwable exception) {
                    logger.warning("LOGOUT FAILED "+exception.getMessage());
                }

                @Override
                public void onSuccess(Method method, Void response) {
                    logger.info("LOGOUT SUCCESSFUL");
                }
            });
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    @Override
    public void getUserInfo(MethodCallback<UserInfoResponse> uiCallback) {
        logger.info("Get userInfo begins...");

        UserInfoClient client = getUserInfoClient();

        MethodCallback<UserInfoResponseImpl> callback = new MethodCallback<UserInfoResponseImpl>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                logger.severe("FAILED TO RETRIEVE USER DATA. ERROR "+exception.getClass().getName()+" MSG "+exception.getMessage());
                uiCallback.onFailure(method,exception);
            }

            @Override
            public void onSuccess(Method method, UserInfoResponseImpl response) {
                uiCallback.onSuccess(method,response);
            }
        };
        client.getUserInfo(callback);

        logger.info("Get userInfo ends...");
    }

    /**
     * @return a instance of the user info client
     */
    private UserInfoClient getUserInfoClient() {
        Resource resource = new Resource( USER_INFO_URL);

        UserInfoClient client = GWT.create(UserInfoClient.class);
        ((RestServiceProxy) client).setResource(resource);
        return client;
    }

    @Override
    public String getCookiePath() {
        return COOKIE_PATH;
    }
}
