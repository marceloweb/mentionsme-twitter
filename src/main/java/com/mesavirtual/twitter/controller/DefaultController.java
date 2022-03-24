package com.mesavirtual.twitter.controller;

import com.mesavirtual.twitter.model.Twitter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@ConfigurationProperties(prefix = "twitter")
@RestController
public class DefaultController {

    @Value("${twitter.api.url}")
    private String twitterUrl;

    @Value("${twitter.api.key}")
    private String twitterKey;

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/search/{keyword}")
    public String search(@PathVariable(value = "keyword") String keyword) {

        String url = this.twitterUrl + "search/recent?query=" + keyword;

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + this.twitterKey);

        RequestEntity<Object> request = new RequestEntity<>(
                headers, HttpMethod.GET, URI.create(url));

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        JSONObject data = new JSONObject(response);

        return data.getString("body").toString();
    }

}
