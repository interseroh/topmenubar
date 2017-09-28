package de.interseroh.tmb.user.client;

import com.google.gwt.core.client.GWT;
import de.interseroh.tmb.user.client.domain.UserInfoClient;
import org.fusesource.restygwt.client.*;
import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.base.ComplexWidget;
import org.gwtbootstrap3.client.ui.constants.ButtonType;

import java.util.Map;
import java.util.logging.Logger;

public class UserInformationServiceImpl implements UserInformationService {

    public final String OID_CONNECT_GATEWAY_LOCATION;
    public final String USER_INFO_URL;
    private final Logger logger;

    public UserInformationServiceImpl(String gatewayLocation, String userInfoUrl, Logger logger) {
        OID_CONNECT_GATEWAY_LOCATION=gatewayLocation;
        USER_INFO_URL = userInfoUrl;
        this.logger = logger;
    }

    @Override
    public ComplexWidget createLoginButton() {
        AnchorButton loginButton = new AnchorButton(ButtonType.fromStyleName("fa-user"));

        loginButton.getElement().addClassName("userLogin");
        loginButton.setHref(OID_CONNECT_GATEWAY_LOCATION);

        return loginButton;
    }

    @Override
    public void getUserInfo(MethodCallback<UserInfoResponse> uiCallback) {
        logger.info("Get userInfo begins...");

        Resource resource = new Resource( USER_INFO_URL);

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

        UserInfoClient client = GWT.create(UserInfoClient.class);
        ((RestServiceProxy) client).setResource(resource);
        client.getUserInfo(callback);



/*        resource.get().send(new JsonCallback() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                logger.severe("FAILED TO RETRIEVE USER DATA. ERROR "+throwable.getClass().getName()+" MSG "+throwable.getMessage());
                uiCallback.onFailure(method, throwable);
            }

            @Override
            public void onSuccess(Method method, JSONValue jsonValue) {
                AutoBeanFactory beanFactory = GWT.create(AutoBeanFactory.class);
                AutoBean<UserInfoResponse> autoBeanCloneAB =  AutoBeanCodex.decode(beanFactory, UserInfoResponse.class, jsonValue.toString());
                UserInfoResponse response = autoBeanCloneAB.as();
                uiCallback.onSuccess(method, response);
            }
        });
*/
        logger.info("Get userInfo ends...");
    }




}
