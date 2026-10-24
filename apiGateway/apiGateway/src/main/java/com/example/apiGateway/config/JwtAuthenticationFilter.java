package com.example.apiGateway.config;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        implements GlobalFilter, Ordered {

    private final RouteValidator validator;
    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String path =
                exchange.getRequest()
                        .getURI()
                        .getPath();
        String method =
                exchange.getRequest()
                        .getMethod()
                        .name();

        if (validator.isSecured.test(path)) {

            if (!exchange.getRequest()
                    .getHeaders()
                    .containsKey(HttpHeaders.AUTHORIZATION)) {

                exchange.getResponse()
                        .setStatusCode(HttpStatus.UNAUTHORIZED);

                return exchange.getResponse()
                        .setComplete();
            }

            String authHeader =
                    exchange.getRequest()
                            .getHeaders()
                            .getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null ||
                    !authHeader.startsWith("Bearer ")) {

                exchange.getResponse()
                        .setStatusCode(HttpStatus.UNAUTHORIZED);

                return exchange.getResponse()
                        .setComplete();
            }

            String token =
                    authHeader.substring(7);

            if (!jwtUtil.validateToken(token)) {

                exchange.getResponse()
                        .setStatusCode(HttpStatus.UNAUTHORIZED);

                return exchange.getResponse()
                        .setComplete();
            }

            Claims claims =
                    jwtUtil.extractClaims(token);

            String username =
                    claims.getSubject();

            String headerUsername =
                    exchange.getRequest()
                            .getHeaders()
                            .getFirst("X-User-Name");
            System.out.println("headerUsername"+ headerUsername);
            System.out.println("username"+ username);


            if (headerUsername == null ||
                    !username.equals(headerUsername)) {

                System.out.println(
                        "Username Mismatch");

                exchange.getResponse()
                        .setStatusCode(HttpStatus.UNAUTHORIZED);

                return exchange.getResponse()
                        .setComplete();
            }

            String role =
                    claims.get("role", String.class);

            System.out.println(
                    "User : " + username +
                            " Role : " + role);
            // ADMIN Access Example

            // Product APIs -> ADMIN only
            if (path.startsWith("/products")) {

                boolean readOperation =
                        "GET".equals(method);

                if (!readOperation
                        && !"ADMIN".equals(role)) {

                    exchange.getResponse()
                            .setStatusCode(HttpStatus.FORBIDDEN);

                    return exchange.getResponse()
                            .setComplete();
                }
            }
            if (path.startsWith("/cart")
                    && !"CUSTOMER".equals(role)) {

                exchange.getResponse()
                        .setStatusCode(HttpStatus.FORBIDDEN);

                return exchange.getResponse()
                        .setComplete();
            }
            // Order APIs
            if (path.startsWith("/orders")
                    && !path.contains("/status")
                    && !"CUSTOMER".equals(role)) {

                exchange.getResponse()
                        .setStatusCode(HttpStatus.FORBIDDEN);

                return exchange.getResponse().setComplete();
            }

// Update Order Status API -> ADMIN only
            if (path.contains("/status")
                    && path.startsWith("/orders")
                    && !"ADMIN".equals(role)) {

                exchange.getResponse()
                        .setStatusCode(HttpStatus.FORBIDDEN);

                return exchange.getResponse()
                        .setComplete();
            };

            }


        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}