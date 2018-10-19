package com.durain.bootu.apigateway.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.durain.bootu.apigateway.constant.CookieConstant;
import com.durain.bootu.apigateway.constant.RedisConstant;
import com.durain.bootu.apigateway.utils.CookieUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class AuthorizationFilter extends ZuulFilter {

	@Autowired
	private StringRedisTemplate strRedisTemplate;

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

		if ("/order-service/orders/new".equals(request.getRequestURI())) {
			Cookie cookie = CookieUtil.get(request, CookieConstant.OPENID);
			if (cookie == null || StringUtils.isEmpty(cookie.getValue())) {
				requestContext.setSendZuulResponse(false);
				requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
			}
		}

		if ("/order-service/orders/complete".equals(request.getRequestURI())) {
			Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
			if (cookie == null || StringUtils.isEmpty(cookie.getValue()) || StringUtils.isEmpty(strRedisTemplate
					.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue())))) {
				requestContext.setSendZuulResponse(false);
				requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
			}
		}

		return null;
	}

}
