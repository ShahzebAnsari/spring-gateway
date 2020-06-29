package com.example.springgateway.customfilters;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("Pre custom Global Filter");
        ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
        builder.header("Global_Filter_Header", "Global_Filter_value").build();

        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            System.out.println("Post custom Global Filter");
            ServerHttpResponse response = exchange.getResponse();
            ResponseCookie.ResponseCookieBuilder builder1 = ResponseCookie.from("cookie_name","cookie_value");
            ResponseCookie cookie = builder1.build();
            response.addCookie(cookie);
        }));
    }

    @Override
    public int getOrder() {
        return 2;
    }
}

