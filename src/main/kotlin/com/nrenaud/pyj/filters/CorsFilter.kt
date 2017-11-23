package com.nrenaud.pyj.filters

import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

/**
 * The Cors Filter from https://sandstorm.de/de/blog/post/cors-headers-for-spring-boot-kotlin-webflux-reactor-project.html
 *
 * This class aims to handle the CORS challenge from the navigator.
 *
 */
@Component
class CorsFilter : WebFilter {
    override fun filter(ctx: ServerWebExchange?, chain: WebFilterChain?): Mono<Void> {
        if (ctx == null) {
            return chain?.filter(null) ?: Mono.empty()
        }
        ctx.response.headers.add("Access-Control-Allow-Origin", "*")
        ctx.response.headers.add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS")
        ctx.response.headers.add("Access-Control-Allow-Headers", "DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range")

        return when(ctx.request.method) {
            HttpMethod.OPTIONS -> {
                ctx.response.headers.add("Access-Control-Max-Age", "1728000")
                ctx.response.statusCode = HttpStatus.NO_CONTENT
                Mono.empty()
            }
            else ->  chain?.filter(ctx) ?: Mono.empty()
        }
    }
}