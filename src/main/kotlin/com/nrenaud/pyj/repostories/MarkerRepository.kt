package com.nrenaud.pyj.repostories

import com.nrenaud.pyj.models.Marker
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface MarkerRepository : ReactiveMongoRepository<Marker, String> {

    fun findAllByGroupId(groupId : String) : Flux<Marker>
}