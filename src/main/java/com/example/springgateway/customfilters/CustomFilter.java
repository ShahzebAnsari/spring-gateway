package com.example.springgateway.customfilters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            builder.header("request_header6","night").build();
            if(true)
            {
                ServerHttpResponse response = exchange.getResponse();
                response.getHeaders().add("header1","value1");
                response.setStatusCode(HttpStatus.OK);
                return response.setComplete();
            }
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                ServerHttpResponse response = exchange.getResponse();
                HttpStatus statusCode= response.getStatusCode();
                System.out.println(statusCode);
            }));
        };

    }

    public static class Config {
    }
}
