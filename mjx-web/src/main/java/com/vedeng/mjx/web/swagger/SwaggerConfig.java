package com.vedeng.mjx.web.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: Wyatt
 * @Date: 2019-06-20 13:54
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("Authorization").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vedeng.mjx.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }
   /* @Bean
    public Docket workflowApi() {
        ApiInfo apiInfo = new ApiInfoBuilder().title("TBI API").description("事件引擎相关API")
                .version("1.0")
                .termsOfServiceUrl("NO terms of service")
                .contact(new Contact("刘洋", "公司网址","公司邮箱"))
                .license("授权码")
                .licenseUrl("授权地址")
                .build();

        return new Docket(DocumentationType.SWAGGER_2).groupName("事件引擎")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false).forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tbi.master.eventEngine.controller"))
                .paths(PathSelectors.any()).build().pathMapping("/")
                .globalOperationParameters(setHeaderToken()).apiInfo(apiInfo);
    }*/
}

