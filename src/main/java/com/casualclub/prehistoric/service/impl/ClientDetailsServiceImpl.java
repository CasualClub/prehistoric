package com.casualclub.prehistoric.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Service
public class ClientDetailsServiceImpl extends JdbcClientDetailsService {

    @Resource
    private DataSource dataSource;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public ClientDetailsServiceImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        ClientDetails clientDetails = (ClientDetails) redisTemplate.opsForValue().get("sys_client:" + clientId);
        if (ObjectUtils.isEmpty(clientDetails)) {
            clientDetails = getCacheClient(clientId);
        }
        clientDetails.getAuthorizedGrantTypes().add("client_credentials");
        return clientDetails;
    }

    private ClientDetails getCacheClient(String clientId) {
        ClientDetails clientDetails = null;

        try {
            clientDetails = super.loadClientByClientId(clientId);
            if (!ObjectUtils.isEmpty(clientDetails)) {
                redisTemplate.opsForValue().set("sys_client:" + clientId, clientDetails);
            }
        } catch (Exception e) {

        }
        return clientDetails;
    }

}
