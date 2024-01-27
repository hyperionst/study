package com.hyperion.r2bdcwebfluxexample.webflux.Controller

import com.hyperion.r2bdcwebfluxexample.Tutorial
import com.hyperion.r2bdcwebfluxexample.webflux.service.TutorialService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/webflux")
class TutorialController (private val tutorialService: TutorialService) {

    @GetMapping("/tutorials")
    @ResponseStatus(HttpStatus.OK)
    fun getAllTutorials(@RequestParam(required = false) title: String?): Flux<Tutorial> {
        return if (title == null) tutorialService.findAll()
        else tutorialService.findByTitleContaining(title)
    }


    @GetMapping("/tutorials/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getTutorialById(@PathVariable("id") id: Int): Mono<Tutorial> {
        return tutorialService.findById(id)
    }

    @PostMapping("/tutorials")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTutorial(@RequestBody tutorial: Tutorial): Mono<Tutorial> {
        return tutorialService.save(Tutorial(tutorial.title, tutorial.description, false))
    }

    @PutMapping("/tutorials/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateTutorial(@PathVariable("id") id: Int, @RequestBody tutorial: Tutorial?): Mono<Tutorial> {
        return tutorialService.update(id, tutorial!!)
    }

    @DeleteMapping("/tutorials/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTutorial(@PathVariable("id") id: Int): Mono<Void> {
        return tutorialService.deleteById(id)
    }

    @DeleteMapping("/tutorials")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAllTutorials(): Mono<Void> {
        return tutorialService.deleteAll()
    }

    @GetMapping("/tutorials/published")
    @ResponseStatus(HttpStatus.OK)
    fun findByPublished(): Flux<Tutorial> {
        return tutorialService.findByPublished(true)
    }

}