package com.hyperion.r2bdcwebfluxexample.coroutine.service

import com.hyperion.r2bdcwebfluxexample.Tutorial
import com.hyperion.r2bdcwebfluxexample.coroutine.repository.TutorialRepository2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*



@Service
class TutorialService2(private val tutorialRepository: TutorialRepository2) {



    //완전히 Mono를 벗겨버린상태로
    //awaitSingle은  otlinx.coroutines.reactor.awaitSingle 다!!
    suspend fun findAll(): List<Tutorial> {
        return tutorialRepository.findAll().collectList().awaitSingle()
    }

    //모노를 나둔 상태로...
    suspend fun findByTitleContaining(title: String): Mono<List<Tutorial>> {
        return tutorialRepository.findByTitleContaining(title).collectList()
    }

    //플럭스를 그대로 둔 상태
    //asFlow로 바꿔야하는건 아닐지?
//    suspend fun findByPublished(isPublished : Boolean) : Flux<Tutorial> {
//        return tutorialRepository.findByPublished(isPublished)
//    }
    suspend fun findByPublished(isPublished : Boolean) : Flow<Tutorial> {
        return tutorialRepository.findByPublished(isPublished).asFlow()
    }


    suspend fun findById(id: Int): Tutorial? {
        return tutorialRepository.findById(id).awaitSingleOrNull()
    }

    suspend fun save(tutorial: Tutorial): Mono<Tutorial> {
        return tutorialRepository.save(tutorial)
    }

    suspend fun update(id: Int, tutorial: Tutorial): Mono<Tutorial> {
        return tutorialRepository.findById(id).map { value: Tutorial ->
            Optional.of(value)
        }.defaultIfEmpty(Optional.empty())
            .flatMap { optionalTutorial: Optional<Tutorial> ->
                run {
                    if (optionalTutorial.isPresent) {
                        tutorial.id = id
                        return@flatMap tutorialRepository.save(tutorial)
                    }
                    return@flatMap Mono.empty()
                }
            }
    }

    suspend fun deleteById(id: Int): Mono<Void> {
        return tutorialRepository.deleteById(id)
    }

    fun deleteAll(): Mono<Void> {
        return tutorialRepository.deleteAll()
    }


}