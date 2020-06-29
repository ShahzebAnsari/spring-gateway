package com.example.springgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                        .route(r -> r.path("/newPath").or().path("/hkash").and().query("name","[^a]*")
                                .filters(f -> f.addResponseHeader("response_header1","red")
                                        .addResponseHeader("response_header2", "black"))
                                .uri("http://localhost:8082/")
                                .id("second-service-new-path"))
                .route(r -> r.path("/{segment}/newPath")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8082/").id("second-service-stri-path")).build();
    }
}
