package com.allreviews.platform.apigateway.filter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GatewayPreFilter extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
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
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String requestURL = request.getRequestURL().toString();
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        logger.info(String.format("%s request to %s", request.getMethod(), requestURL));

        if(requestURL.contains("login")) {
            return null;
        } else if(authorizationHeader == null || !authorizationHeader.contains("bearer")) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseBody("api key not authorized");
            ctx.getResponse().setHeader("Content-Type", "text/plain;charset=UTF-8");
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }  else {
            String token = authorizationHeader.substring(7);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("token", token);

            try {
                HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
                ResponseEntity<Map> responseEntity = restTemplate.postForEntity("http://localhost:8101/oauth/check_token", requestEntity, Map.class);
                // Map<String, Object> result = responseEntity.getBody();

                HttpStatus statusCode = responseEntity.getStatusCode();
                if (statusCode == HttpStatus.OK) {
                    ctx.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, authorizationHeader);

                    return null;
                }
            } catch(Exception e) {
                ctx.setSendZuulResponse(false);
                ctx.setResponseBody(e.getMessage());
                ctx.getResponse().setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }

        return null;
    }
}
