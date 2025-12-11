package com.ecommerce.maingateway.routes;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.util.function.Function;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;
import static org.springframework.web.servlet.function.RequestPredicates.method;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.removeRequestHeader;
@Configuration
public class Routes {

    private Function<ServerRequest, ServerRequest> addSecurityHeader() {
        return serverRequest -> {
            ServletRequestAttributes attrs =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attrs != null) {
                HttpServletRequest servletRequest = attrs.getRequest();

                Object roleAttr = servletRequest.getAttribute("X-User-Roles");
                if (roleAttr != null) {
                    return ServerRequest.from(serverRequest)
                            .headers(headers -> headers.add("X-User-Roles", roleAttr.toString()))
                            .build();
                }
            }

            return serverRequest;
        };
    }


    @Bean
    public RouterFunction<ServerResponse> authServiceRoute() {
        return route("auth_service_main")
                .route(req -> path("/api/auth/**").test(req)
                                || req.method().equals(HttpMethod.OPTIONS),
                        HandlerFunctions.http("lb://auth-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "authService", URI.create("forward:/fallbackRoute")
                ))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> uploadService() {
        return route("image_upload_main")
                .route(req->path("/api/document/**").test(req)
                || req.method().equals(HttpMethod.OPTIONS),
                        HandlerFunctions.http("lb://upload-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("uploadService",URI.create("forward:/fallbackRoute")
                        )
                ).build();
    }
    @Bean
    public RouterFunction<ServerResponse> productServiceRoute() {
        return route("product_service_main")
                .route(req -> path("/api/product/**").test(req)
                                || req.method().equals(HttpMethod.OPTIONS),
                        HandlerFunctions.http("lb://product-service"))
                .before(addSecurityHeader())
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "productService", URI.create("forward:/fallbackRoute")
                ))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute() {
        return route("inventory_service_main")
                .route(req -> path("/api/inventory/**").test(req)
                                || req.method().equals(HttpMethod.OPTIONS),
                        HandlerFunctions.http("lb://inventory-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "inventoryService", URI.create("forward:/fallbackRoute")
                ))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {
        return route("order_service_main")
                .route(req -> path("/api/order/**").test(req)
                                || req.method().equals(HttpMethod.OPTIONS),
                        HandlerFunctions.http("lb://order-service"))
                .before(addSecurityHeader())
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "orderService", URI.create("forward:/fallbackRoute")
                ))
                .build();
    }

//    @Bean
//    public RouterFunction<ServerResponse> notificationService() {
//        return route("notification_service_main")
//                .route(req->path("/api/notification/**")
//                        .test(req) || req.method().equals(HttpMethod.OPTIONS),
//                        HandlerFunctions.http("lb://notification-service"))
//                .filter(CircuitBreakerFilterFunctions.circuitBreaker("notification-service",
//                        URI.create("forward://fallbackRoute")))
//                .build();
//    }
    // ------- SWAGGER ROUTES -------
    @Bean
    public RouterFunction<ServerResponse> authServiceSwaggerRoute() {
        return route("auth_service_swagger")
                .route(method(HttpMethod.GET)
                                .and(path("/aggregate/auth-service/v3/api-docs")),
                        HandlerFunctions.http("lb://AUTH-SERVICE"))
                .filter(setPath("/v3/api-docs"))
                .before(removeRequestHeader("Authorization"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productServiceSwaggerRoute() {
        return route("product_service_swagger")
                .route(method(HttpMethod.GET)
                                .and(path("/aggregate/product-service/v3/api-docs")),
                        HandlerFunctions.http("lb://PRODUCT-SERVICE"))
                .filter(setPath("/v3/api-docs"))
                .before(removeRequestHeader("Authorization"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute() {
        return route("inventory_service_swagger")
                .route(method(HttpMethod.GET)
                                .and(path("/aggregate/inventory-service/v3/api-docs")),
                        HandlerFunctions.http("lb://INVENTORY-SERVICE"))
                .filter(setPath("/v3/api-docs"))
                .before(removeRequestHeader("Authorization"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceSwaggerRoute() {
        return route("order_service_swagger")
                .route(method(HttpMethod.GET)
                                .and(path("/aggregate/order-service/v3/api-docs")),
                        HandlerFunctions.http("lb://ORDER-SERVICE"))
                .filter(setPath("/v3/api-docs"))
                .before(removeRequestHeader("Authorization"))
                .build();
    }

//    @Bean
//    public RouterFunction<ServerResponse> notificationServiceSwaggerRoute() {
//        return route("notification_service_swagger")
//                .route(method(HttpMethod.GET)
//                                .and(path("/aggregate/notification-service/v3/api-docs")),
//                        HandlerFunctions.http("lb://NOTIFICATION-SERVICE"))
//                .filter(setPath("/v3/api-docs"))
//                .before(removeRequestHeader("Authorization"))
//                .build();
//    }
    @Bean
    public RouterFunction<ServerResponse> uploadServiceSwaggerRoute() {
        return route("order_service_swagger")
                .route(method(HttpMethod.GET)
                                .and(path("/aggregate/upload-service/v3/api-docs")),
                        HandlerFunctions.http("lb://UPLOAD-SERVICE"))
                .filter(setPath("/v3/api-docs"))
                .before(removeRequestHeader("Authorization"))
                .build();
    }
    // ------- FALLBACK ROUTE -------

    @Bean
    public RouterFunction<ServerResponse> fallBackRoute() {
        return route("fallback_route")
                .route(path("/fallbackRoute"),
                        req -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("Service is Not Available, please try again ..."))
                .build();
    }

}
