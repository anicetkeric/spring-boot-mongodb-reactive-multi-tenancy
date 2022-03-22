package com.boottech.springbootmongodbreactivemultitenancy.filter;

import com.boottech.springbootmongodbreactivemultitenancy.common.AppConstant;
import com.boottech.springbootmongodbreactivemultitenancy.common.exception.TenantDataSourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TenantInterceptor implements WebFilter {


   @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (request.getHeaders().containsKey(AppConstant.TENANT_HEADER)) {
            String tenant = request.getHeaders().getFirst(AppConstant.TENANT_HEADER);
            TenantContext.set(tenant);
        }else {
            throw new TenantDataSourceNotFoundException("Tenant not found header");
        }



        return chain.filter(exchange).doFinally( t -> TenantContext.clear());
    }
}