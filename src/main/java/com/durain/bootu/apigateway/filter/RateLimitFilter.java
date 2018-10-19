package com.durain.bootu.apigateway.filter;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class RateLimitFilter extends ZuulFilter {

	private static final RateLimiter RATE_LIMTER = RateLimiter.create(100);

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.SERVLET_DETECTION_FILTER_ORDER - 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext requestContext = RequestContext.getCurrentContext();
		if (!RATE_LIMTER.tryAcquire()) {
			requestContext.setSendZuulResponse(false);
			requestContext.setResponseStatusCode(HttpStatus.BAD_REQUEST.value());
		}
		return null;
	}

}
