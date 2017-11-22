package com.nrenaud.pyj

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class ConfigurationPYJ {

    val searchByid = "uuid"

    companion object {
        val LOG : Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Bean
    fun routerFunction(handler: ReactiveHandler):
            RouterFunction<ServerResponse> = router {

        ("/api").nest {
            GET("/") {
                LOG.debug("TOUT LISTER")
                ServerResponse.ok().body(handler.getAllMarkers())
            }
            GET("/{$searchByid}") { req ->
                val uuid = req.pathVariable(searchByid)
                LOG.debug("TOUT LISTER pour $searchByid")
                ServerResponse.ok().body(handler.getMarkers(uuid))
            }
            POST("/{$searchByid}") { req ->
                val uuid = req.pathVariable(searchByid)
                val markerToAdd = req.bodyToMono(Marker::class.java)
                LOG.debug("Ajouter pour $uuid")
                handler.addMarkers(uuid, markerToAdd)
                ServerResponse.ok().build()
            }
        }
    }
}
