package com.msys.digitalwallet.notification.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class LoggingFilter  extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);


    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
                request.getCharacterEncoding());
        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
                response.getCharacterEncoding());

        LOGGER.info(
                "METHOD={}; REQUEST URI={}; REQUEST PAYLOAD={}; RESPONSE CODE={}; RESPONSE={};",
                request.getMethod(), request.getRequestURI(), requestBody, response.getStatus(), responseBody);
        responseWrapper.copyBodyToResponse();

        filterChain.doFilter(request,response);
    }

//    @Override
//    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
//        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
//        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
//
//        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
//                request.getCharacterEncoding());
//        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
//                response.getCharacterEncoding());
//
//        LOGGER.info(
//                "METHOD={}; REQUEST URI={}; REQUEST PAYLOAD={}; RESPONSE CODE={}; RESPONSE={};",
//                request.getMethod(), request.getRequestURI(), requestBody, response.getStatus(), responseBody);
//        responseWrapper.copyBodyToResponse();
//
//        filterChain.doFilter(requestWrapper, responseWrapper);
//    }
}
