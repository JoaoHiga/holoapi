package com.takomuraragi.holoapi.holoapi.services;

import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.net.URISyntaxException;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class UrlBuilder {
    public String buildURL (String baseUrl, Map<String, String> queryParameters) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(baseUrl);
        return  uriBuilder.addParameters(queryParameters.entrySet().stream()
                        .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                        .collect(toList()))
                .toString();
    }
}
