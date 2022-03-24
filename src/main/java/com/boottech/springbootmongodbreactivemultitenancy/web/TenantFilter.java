package com.boottech.springbootmongodbreactivemultitenancy.web;

import com.boottech.springbootmongodbreactivemultitenancy.common.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TenantFilter implements WebFilter {

   @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
       return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(AppConstant.TENANT_HEADER_KEY))
               .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)))
               .flatMap(tenantKey -> chain.filter(exchange).contextWrite(ctx -> ctx.put(AppConstant.TENANT_ID, tenantKey)));

    }
}