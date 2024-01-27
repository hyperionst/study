package com.hyperion.r2bdcwebfluxexample.webflux.service

import com.hyperion.r2bdcwebfluxexample.Tutorial
import com.hyperion.r2bdcwebfluxexample.webflux.repository.TutorialRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class TutorialService(private val tutorialRepository: TutorialRepository) {

    fun findAll(): Flux<Tutorial> {
        return tutorialRepository.findAll()
    }

    fun findByTitleContaining(title: String): Flux<Tutorial> {
        return tutorialRepository.findByTitleContaining(title)
    }

    fun findById(id: Int): Mono<Tutorial> {
        return tutorialRepository.findById(id)
    }

    fun save(tutorial: Tutorial): Mono<Tutorial> {
        return tutorialRepository.save(tutorial)
    }

    fun update(id: Int, tutorial: Tutorial): Mono<Tutorial> {
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

    fun findByPublished(isPublished : Boolean) : Flux<Tutorial> {
        return tutorialRepository.findByPublished(isPublished)
    }

    fun deleteById(id: Int): Mono<Void> {
        return tutorialRepository.deleteById(id)
    }

    fun deleteAll(): Mono<Void> {
        return tutorialRepository.deleteAll()
    }


}