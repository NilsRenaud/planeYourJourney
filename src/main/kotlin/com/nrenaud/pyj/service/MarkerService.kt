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

    fun addMarker(groupUuid: String, markerToAdd: Mono<MarkerDto>) {
        markerToAdd.subscribe({
            repo.save(it.toDbEntity(groupUuid))
        })
    }

    fun getMarkers(groupUuid: String)  : Flux<MarkerDto> = repo.findAllByGroupId(groupUuid).map { it.toDto() }

    fun findAll() : Flux<MarkerDto>  {
        return repo.findAll().map { it.toDto() }
    }
}

fun Marker.toDto() = MarkerDto(this.lat, this.lng)

fun MarkerDto.toDbEntity(groupUuid :String) =
        Marker(groupUuid, this.lat, this.lng, UUID.randomUUID().toString())