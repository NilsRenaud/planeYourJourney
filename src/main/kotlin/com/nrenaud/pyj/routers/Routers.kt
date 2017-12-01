package com.nrenaud.pyj.routers

import com.nrenaud.pyj.handlers.MarkerHandlers
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Component
class Routers(private val handler : MarkerHandlers) {

    fun markerRouter():
            RouterFunction<ServerResponse> = router {
        ("/api" and accept(APPLICATION_JSON)).nest {
            GET("/markers", handler::getMarkers)
            POST("/markers", handler::saveMarker)
            PUT("/markers", handler::updateMarker)
        }
    }
}
