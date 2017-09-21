package de.interseroh.tmb.profile.client.common;

import de.interseroh.tmb.profile.client.domain.AgeClient;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ingo Düppe (CROWDCODE)
 */

@Singleton
public class ServicePreparator {

    private static final Logger logger = Logger
            .getLogger(ServicePreparator.class.getName());


    private final AgeClient ageClient;

    @Inject
    public ServicePreparator(AgeClient appClient) {
        this.ageClient = appClient;
    }

    private void initServices(String appUrl) {
        logger.info("Prepare for the resources for the services...");

        Defaults.setDateFormat(null);

        initDomainService(appUrl);
    }

    private void initDomainService(String appUrl) {
        logger.info("Init  the domains...");

        Resource resource = new Resource(appUrl == null ? "" : appUrl);
        ((RestServiceProxy) ageClient).setResource(resource);

    }

    public AgeClient getAgeClient() {
        return ageClient;
    }

    public void prepare(String appUrl) {
        initServices(appUrl);
    }

}
