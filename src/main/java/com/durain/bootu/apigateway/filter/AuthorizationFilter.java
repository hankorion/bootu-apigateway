package com.durain.bootu.apigateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class AuthorizationFilter extends ZuulFilter{

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}
	
	
	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
	}
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		
		
		
		return null;
	}


}
