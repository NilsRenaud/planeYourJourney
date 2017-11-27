package com.nrenaud.pyj.service

import com.nrenaud.pyj.models.Marker
import com.nrenaud.pyj.models.MarkerDto
import com.nrenaud.pyj.repostories.MarkerRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Component
class MarkerService(private val repo : MarkerRepository) {

    fun addMarker(groupUuid: String, markerToAdd: Mono<MarkerDto>) : Mono<MarkerDto> {
        return markerToAdd.map { it.toDbEntity(groupUuid) }
                .flatMap {repo.insert(it)}
                .map { it.toDto() }
    }

    fun updateMarker(groupUuid: String, markerToAdd: Mono<MarkerDto>) : Mono<Void>{
        return markerToAdd.map { it.toDbEntity(groupUuid) }
                .flatMap {repo.save(it)}
                .then()
    }

    fun getMarkers(groupUuid: String)  : Flux<MarkerDto> = repo.findAllByGroupId(groupUuid).map { it.toDto() }

    fun findAll() : Flux<MarkerDto>  = repo.findAll().map { it.toDto() }
}

fun Marker.toDto() = MarkerDto(this.id, this.lat, this.lng)

fun MarkerDto.toDbEntity(groupUuid :String) =
        Marker(groupUuid, this.lat, this.lng, this.id?: UUID.randomUUID().toString())

