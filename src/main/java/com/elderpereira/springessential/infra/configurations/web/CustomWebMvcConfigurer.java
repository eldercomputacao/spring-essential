package com.elderpereira.springessential.infra.configurations.web;

import com.elderpereira.springessential.infra.configurations.web.convert.YamlJackson2HttpMesageConverter;
import com.elderpereira.springessential.util.MediaTypeYmal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${cors.origin-patterns}")
    private String corsOriginPatterns;

    // Added CORS global
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOrigins = corsOriginPatterns.split(",");
        registry.addMapping("/**")
                //.allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedMethods("*")
                .allowedOrigins(allowedOrigins)
                .allowCredentials(true);
    }

    // Added content negotiation
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // OPÇÃO 01 -> VIA QUERY PARAM
        // ?mediaType=xml
//        configurer.favorParameter(true)
//                .parameterName("mediaType")
//                .ignoreAcceptHeader(true)
//                .useRegisteredExtensionsOnly(false)
//                .defaultContentType(MediaType.APPLICATION_JSON)
//                .mediaType("json", MediaType.APPLICATION_JSON)
//                .mediaType("xml", MediaType.APPLICATION_XML);

        // OPÇÃO 02 -> VIA HEADER PARAM
        // Accept = application/xml
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("x-yaml", MediaTypeYmal.APPLICATION_YAML);
    }

    // Added support content negotiation for YAML
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new YamlJackson2HttpMesageConverter());
    }
}
