package de.interseroh.tmb.common;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexadmin on 03.02.2017.
 */
@Component
public class LoggingCrossOrigin {

    @Bean(name="loggingFilter")
    public CrossOriginLoggingFilter getCrossOriginFilter(){
        CrossOriginLoggingFilter filter = new CrossOriginLoggingFilter();
        return filter;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        List<String> urls= new ArrayList<>();
        Filter filter =getCrossOriginFilter();
        //urls.add("/applauncher/applauncher/remote_logging");
        urls.add("/*/remote_logging");
        registration.setUrlPatterns(urls);
        registration.setFilter(filter);
        return registration;
    }
}
