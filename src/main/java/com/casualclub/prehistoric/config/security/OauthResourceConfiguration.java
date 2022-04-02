package com.casualclub.prehistoric.config.security;

import com.casualclub.prehistoric.config.handle.MyAccessDeniedHandle;
import com.casualclub.prehistoric.config.handle.UnauthorizedHandler;
import com.casualclub.prehistoric.properties.PermitUrlProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Order(5)
@EnableResourceServer
//@EnableAutoConfiguration(exclude = UserDetailsServiceAutoConfiguration.class)
public class OauthResourceConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private PermitUrlProperties permitUrlProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(new UnauthorizedHandler(objectMapper))
                .accessDeniedHandler(new MyAccessDeniedHandle(objectMapper));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config
                = http.requestMatchers().anyRequest()
                .and()
                .authorizeRequests();
        permitUrlProperties.getUrls().forEach(config::antMatchers);
        permitUrlProperties.getPermitUrls().forEach(config::antMatchers);

        config.anyRequest().authenticated().and().csrf().disable().headers().frameOptions().disable();
    }
}
