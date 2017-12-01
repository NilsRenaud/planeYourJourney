package com.nrenaud.pyj.service

import com.nrenaud.pyj.models.Marker
import com.nrenaud.pyj.models.MarkerDto
import com.nrenaud.pyj.repostories.MarkerRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class MarkerService(private val repo : MarkerRepository) {

    fun addMarker(markerToAdd: Mono<MarkerDto>) : Mono<MarkerDto> {
        return markerToAdd.map { it.toDbEntity() }
                .flatMap {repo.insert(it)}
                .map { it.toDto() }
    }

    fun updateMarker(markerToAdd: Mono<MarkerDto>) : Mono<Void>{
        return markerToAdd.map { it.toDbEntity() }
                .flatMap {repo.save(it)}
                .then()
    }

    fun getMarkers(groupUuid: String)  : Flux<MarkerDto> = repo.findAllByGroupId(groupUuid).map { it.toDto() }

    fun findAll() : Flux<MarkerDto>  = repo.findAll().map { it.toDto() }
}

fun Marker.toDto() = MarkerDto(this.id, this.groupId, this.name, this.interestId, this.lat, this.lng)

fun MarkerDto.toDbEntity() =
        Marker(this.id?: UUID.randomUUID().toString(), this.groupId, this.name, this.interestId, this.lat, this.lng)

