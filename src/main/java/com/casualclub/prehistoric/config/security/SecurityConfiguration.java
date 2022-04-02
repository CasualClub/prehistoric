package com.casualclub.prehistoric.config.security;

import com.casualclub.prehistoric.config.handle.CommonAuthenticationFailureHandler;
import com.casualclub.prehistoric.config.handle.CommonAuthenticationSuccessHandler;
import com.casualclub.prehistoric.properties.PermitUrlProperties;
import com.casualclub.prehistoric.service.security.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;

/**
 * @author pgolds
 * @Version 1.0.0
 * @date 2021.09.27
 * @description security配置
 */
@Order(3)
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private PermitUrlProperties permitUrlProperties;

    /**
     * 密码编码器Bean
     * @return passwordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public AuthenticationSuccessHandler commonAuthenticationSuccessHandler() {
        return new CommonAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler commonAuthenticationFailureHandler() {
        return new CommonAuthenticationFailureHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config =
                http.requestMatchers().anyRequest()
                                .and().formLogin()
                                .and().authorizeRequests();
        // 放行路径
        permitUrlProperties.getUrls().forEach(config::antMatchers);
        permitUrlProperties.getPermitUrls().forEach(config::antMatchers);

        config.anyRequest().authenticated().and().csrf().disable().headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .withUser("admin")
//                .password("123321")
//                .roles("USER");
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }


}
