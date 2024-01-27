package com.hyperion.r2bdcwebfluxexample.webflux.repository

import com.hyperion.r2bdcwebfluxexample.Tutorial
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

//The implementation is plugged in by Spring Data R2DBC automatically.
@Repository
interface TutorialRepository : R2dbcRepository<Tutorial, Int> {

    //returns all Tutorials which title contains input title
    fun findByTitleContaining(title : String) : Flux<Tutorial>

    //returns all Tutorials with published having value as input published
    fun findByPublished(isPublished : Boolean ) : Flux<Tutorial>
}
