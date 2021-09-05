package com.casualclub.prehistoric.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableSwagger2
@Configuration
@Profile("dev")
public class SwaggerConfig {


    @Bean
    public Docket createRestApi() {
        final List<ResponseMessage> globalResponses = Arrays.asList(
                new ResponseMessageBuilder()
                        .code(200)
                        .message("OK")
                        .build(),
                new ResponseMessageBuilder()
                        .code(400)
                        .message("参数错误")
                        .build(),
                new ResponseMessageBuilder()
                        .code(401)
                        .message("没有登录")
                        .build(),
                new ResponseMessageBuilder()
                        .code(403)
                        .message("没有权限")
                        .build(),
                new ResponseMessageBuilder()
                        .code(500)
                        .message("系统错误")
                        .build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalResponses)
                .globalResponseMessage(RequestMethod.POST, globalResponses)
                .globalResponseMessage(RequestMethod.PUT, globalResponses)
                .globalResponseMessage(RequestMethod.DELETE, globalResponses)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                //配置鉴权信息
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .enable(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("prehistoric")
                .description("prehistoric Api Documentation Center")
                .version("1.0")
                .contact(new Contact("prehistoric", "http://github.com/casualclub", "postmaster@casualclub.com"))
                .build();
    }

    private List<ApiKey> securitySchemes() {
        return Collections.singletonList(new ApiKey("Authorization", "Authorization", "header"));
    }


    private List<SecurityContext> securityContexts() {
        return new ArrayList<>(
                Collections.singleton(SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build())
        );
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList<>(Collections.singleton(new SecurityReference(HttpHeaders.AUTHORIZATION, authorizationScopes)));
    }

}
