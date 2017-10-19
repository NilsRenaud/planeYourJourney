package com.nrenaud.pyj

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux

@Component
class ReactiveHandler(val repo: MarkerRepo) {
    fun getMarkers(uuid: String): Flux<Marker> =
            repo.get(uuid).toFlux()
    fun addMarkers(uuid: String, mono : Mono<Marker>) {
            mono.subscribe({marker -> repo.add(uuid, marker) }) }
    fun getAllMarkers(): Flux<Marker> =
            repo.getAll().toFlux()
}

@Component
class MarkerRepo {
    private val entities = mutableMapOf<String, MutableList<Marker>>()
    fun add(uuid: String, marker : Marker) {
        println("ajouté : $uuid")
        entities.getOrPut(uuid, { mutableListOf() }).add(marker)
    }
    fun get(uuid: String) : List<Marker> {
        println("lue : $uuid")
        return entities.getOrDefault(uuid, mutableListOf())
    }
    fun getAll() : List<Marker> {
        println("tout : $entities")
        return entities.flatMap { it.value }
    }
}

data class Marker(val lat : String, val lng : String)