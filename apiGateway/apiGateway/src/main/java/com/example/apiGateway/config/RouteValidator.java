package com.example.apiGateway.config;



import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/login",
            "/auth/register",
            "/auth/Adminregister",
            "/eureka"
    );

    public Predicate<String> isSecured =
            uri -> openApiEndpoints
                    .stream()
                    .noneMatch(uri::contains);
}
