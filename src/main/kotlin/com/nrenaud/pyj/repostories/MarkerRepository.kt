package com.nrenaud.pyj.repostories

import com.nrenaud.pyj.models.Marker
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface MarkerRepository : ReactiveCrudRepository<Marker, String> {

    fun findAllByGroupId(groupId : String) : Flux<Marker>
}