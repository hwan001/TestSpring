package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example"))//Swagger API 문서로 만들기 원하는 basePackage 경로
                //.paths(PathSelectors.ant("/api/**"))    //URL 경로를 지정하여 해당 URL에 해당하는 요청만 SWAGGER로 만듦
                .paths(PathSelectors.any())    //URL 경로를 지정하여 해당 URL에 해당하는 요청만 SWAGGER로 만듦
                .build();
    }

}
