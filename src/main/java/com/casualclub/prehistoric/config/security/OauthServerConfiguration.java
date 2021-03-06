package com.casualclub.prehistoric.config.security;

import com.casualclub.prehistoric.dto.AuthInfo;
import com.casualclub.prehistoric.service.impl.ClientDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import javax.annotation.Resource;
import java.util.*;

@Order(2)
@Configuration
@EnableAuthorizationServer
public class OauthServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Resource
    private ClientDetailsServiceImpl clientService;

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsService userDetailsService;

    public static final String CLIENT_TABLE = "sys_client";

    /**
     * ??????????????????
     */
    public static final String CLIENT_BASE = "select client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, " +
            "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity," +
            "refresh_token_validity, additional_information, autoapprove from " + CLIENT_TABLE;

    public static final String FIND_CLIENT_DETAIL_SQL = CLIENT_BASE + " order by client_id";

    public static final String SELECT_CLIENT_DETAIL_SQL = CLIENT_BASE + " where client_id = ?";

    @Bean
    public RedisTokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        DefaultTokenServices tokenServices = createDefaultTokenServices();
        // token?????????
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        // ???jwt????????????????????????????????????????????????
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        // ??????tokenServices??????
        addUserDetailsService(tokenServices);
        endpoints
                .tokenGranter(tokenGranter(endpoints, tokenServices))
                .tokenServices(tokenServices)
                .accessTokenConverter(jwtAccessTokenConverter());

    }

    private void addUserDetailsService(DefaultTokenServices tokenServices) {
        if (userDetailsService != null) {
            PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
            provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService));
            tokenServices.setAuthenticationManager(new ProviderManager(Collections.singletonList(provider)));
        }
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // ????????????????????????
                .allowFormAuthenticationForClients()
                // spel????????? ?????????????????????/auth/token_key???????????????
                .tokenKeyAccess("isAuthenticated()")
                // spel????????? ???????????????????????????/auth/check_token???????????????
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clientService.setSelectClientDetailsSql(SELECT_CLIENT_DETAIL_SQL);
        clientService.setFindClientDetailsSql(FIND_CLIENT_DETAIL_SQL);
        clients.withClientDetails(clientService);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("meta");
        return jwtAccessTokenConverter;
    }

    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints, DefaultTokenServices tokenServices) {
        List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));

        // ??????????????????
        granters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        return new CompositeTokenGranter(granters);
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (oAuth2AccessToken, oAuth2Authentication) -> {

            // ?????????????????????map
            final Map<String, Object> additionMessage = new HashMap<>(4);
            // ??????????????????????????????????????????token
            if (oAuth2Authentication.getUserAuthentication() == null) {
                return oAuth2AccessToken;
            }
            // ???????????????????????????
            AuthInfo user = (AuthInfo) oAuth2Authentication.getUserAuthentication().getPrincipal();

            // ????????????????????? ??????id??????jwt token???
            if (user != null) {
                additionMessage.put("userId", String.valueOf(user.getId()));
                additionMessage.put("username", user.getUsername());
                additionMessage.put("avatar", user.getAvatar());
                additionMessage.put("loginMode", user.getType());
            }
            ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionMessage);
            return oAuth2AccessToken;
        };
    }

    /**
     * ???????????????tokenServices
     *
     * @return DefaultTokenServices
     */
    private DefaultTokenServices createDefaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(redisTokenStore());
        // ????????????Token
        tokenServices.setSupportRefreshToken(Boolean.TRUE);
        tokenServices.setReuseRefreshToken(Boolean.FALSE);
        tokenServices.setClientDetailsService(clientService);
        addUserDetailsService(tokenServices);
        return tokenServices;
    }

}
