package com.nrenaud.pyj

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.server.*

@SpringBootApplication
class ConfigurationPYJ {

    val searchByid = "uuid"

    @Bean
    fun routerFunction(handler: ReactiveHandler):
            RouterFunction<ServerResponse> = router {

        ("/api").nest {
            GET("/") {
                System.out.println("TOUT LISTER")
                ServerResponse.ok().body(handler.getAllMarkers())
            }
            GET("/{$searchByid}") { req ->
                val uuid = req.pathVariable(searchByid)
                System.out.println("TOUT LISTER pour $searchByid")
                ServerResponse.ok().body(handler.getMarkers(uuid))
            }
            POST("/{$searchByid}") { req ->
                val uuid = req.pathVariable(searchByid)
                val markerToAdd = req.bodyToMono(Marker::class.java)
                System.out.println("Ajouter pour $searchByid")
                handler.addMarkers(uuid, markerToAdd)
                ServerResponse.ok().build()
            }
        }
    }
}
