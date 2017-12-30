package de.interseroh.tmb.landing.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class SimpleZuulFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(SimpleZuulFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.info(String.format("%s request to %s", request.getMethod(),
				request.getRequestURL().toString()));

		final HttpServletResponse response = ctx.getResponse();
		log.info(String.format("%s response to %s", response.getHeaderNames(),
				response.getStatus()));

		return null;
	}

}
