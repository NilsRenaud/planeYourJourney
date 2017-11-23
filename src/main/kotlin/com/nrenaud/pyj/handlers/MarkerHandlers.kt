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

    fun getAllMarkers(req : ServerRequest): Mono<ServerResponse> {
        LOG.debug("List all markers.")
       return ServerResponse.ok().body(service.findAll())
    }

    fun getMarkerForUuid(req : ServerRequest): Mono<ServerResponse> {
        val uuid = req.pathVariable("uuid")
        LOG.debug("List all markers for [$uuid]")
        return ServerResponse.ok().body(service.getMarkers(uuid))
    }

    fun saveMarker(req : ServerRequest) : Mono<ServerResponse> {
        val uuid = req.pathVariable("uuid")
        LOG.debug("Add new marker for [$uuid]")
        service.addMarker(uuid, req.bodyToMono(MarkerDto::class.java))
        return ServerResponse.ok().build()
    }
}