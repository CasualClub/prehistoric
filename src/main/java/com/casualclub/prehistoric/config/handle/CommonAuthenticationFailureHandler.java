package com.casualclub.prehistoric.config.handle;

import cn.hutool.json.JSONUtil;
import com.casualclub.prehistoric.wrapper.RestResponse;
import com.casualclub.prehistoric.wrapper.Wrapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CommonAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        Wrapper<String> result = null;
        if (exception instanceof BadCredentialsException) {
            result = RestResponse.error("用户名或密码错误");
        } else if (exception instanceof AccountExpiredException) {
            result = RestResponse.error("用户名已过期");
        } else if (exception instanceof CredentialsExpiredException) {
            result = RestResponse.error("密码已过期");
        } else if (exception instanceof DisabledException) {
            result = RestResponse.error("用户已禁用");
        } else if (exception instanceof LockedException) {
            result = RestResponse.error("用户已锁定");
        } else if (exception instanceof InternalAuthenticationServiceException) {
            result = RestResponse.error("系统内部错误");
        } else {
            result = RestResponse.error("系统内部错误");
        }
        response.setContentType(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getOutputStream().write(JSONUtil.toJsonStr(result).getBytes());
    }
}
