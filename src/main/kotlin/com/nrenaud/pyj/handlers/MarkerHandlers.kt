package com.nrenaud.pyj.handlers

import com.nrenaud.pyj.models.MarkerDto
import com.nrenaud.pyj.service.MarkerService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class MarkerHandlers(private val service: MarkerService) {
    companion object {
        val LOG: Logger = LoggerFactory.getLogger(this::class.java)
    }

    fun getMarkers(req: ServerRequest): Mono<ServerResponse> {
        val gUuid = req.queryParam("group")

        LOG.debug("List all markers for : [$gUuid]")

        return ServerResponse.ok().body(
                when (gUuid.isPresent) {
                    true -> service.getMarkers(gUuid.get())
                    false -> service.findAll()
                })
    }

    fun saveMarker(req: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().body(service.addMarker(req.bodyToMono(MarkerDto::class.java)))
    }

    fun updateMarker(req: ServerRequest): Mono<ServerResponse> {
        return service.updateMarker(req.bodyToMono(MarkerDto::class.java))
                .then(ServerResponse.ok().build())
    }
}