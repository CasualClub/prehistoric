package com.casualclub.prehistoric.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "security.ignore")
public class PermitUrlProperties {

    private static final String[] SECURITY_PERMIT_BASIC_URL = {
            "**/favicon.ico",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars/**",
            "/doc.html",
    };

    private List<String> urls = new ArrayList<>();

    private List<String> permitUrls = new ArrayList<>();

    @PostConstruct
    public void initPermitSecurity() {
        Collections.addAll(permitUrls, SECURITY_PERMIT_BASIC_URL);
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getPermitUrls() {
        return permitUrls;
    }

    public void setPermitUrls(List<String> permitUrls) {
        this.permitUrls = permitUrls;
    }
}
