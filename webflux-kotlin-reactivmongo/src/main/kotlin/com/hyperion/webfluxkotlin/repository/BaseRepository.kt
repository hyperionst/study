package com.hyperion.webfluxkotlin.repository

import com.hyperion.webfluxkotlin.entity.BaseDocument
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface BaseRepository : ReactiveMongoRepository<BaseDocument, String> {

    fun findByBaseId(baseId: String) : Flux<BaseDocument>
}