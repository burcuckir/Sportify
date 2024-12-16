package com.sportify.jobscheduler.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public abstract class BaseApiClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public BaseApiClient(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    protected <T> ResponseEntity<T> get(String endpoint, Map<String, String> queryParams, HttpHeaders headers, Class<T> responseType) {
        String url = buildUrl(endpoint, queryParams);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
    }

    protected <T> ResponseEntity<T> post(String endpoint, Object requestBody, HttpHeaders headers, Class<T> responseType) {
        String url = buildUrl(endpoint, null);
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
    }

    protected <T> ResponseEntity<T> put(String endpoint, Object requestBody, HttpHeaders headers, Class<T> responseType) {
        String url = buildUrl(endpoint, null);
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
    }

    protected void delete(String endpoint, Map<String, String> queryParams, HttpHeaders headers) {
        String url = buildUrl(endpoint, queryParams);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    private String buildUrl(String endpoint, Map<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl + endpoint);
        if (queryParams != null) {
            queryParams.forEach(uriBuilder::queryParam);
        }
        return uriBuilder.toUriString();
    }
}

