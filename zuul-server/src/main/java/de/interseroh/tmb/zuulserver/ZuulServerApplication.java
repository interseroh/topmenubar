package de.interseroh.tmb.zuulserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;
import org.springframework.stereotype.Controller;

/**
 * Created by idueppe on 10.02.17.
 */
@Controller
@EnableZuulProxy
@EnableZuulServer
@SpringBootApplication
public class ZuulServerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ZuulServerApplication.class).web(true)
				.run(args);
	}
}
