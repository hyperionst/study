package com.hyperion.r2bdcwebfluxexample.coroutine.controller

import com.hyperion.r2bdcwebfluxexample.Tutorial
import com.hyperion.r2bdcwebfluxexample.coroutine.service.TutorialService2
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono



/**
 *  #WebFlux to Coroutine
 *
 *  [https://www.baeldung.com/kotlin/spring-boot-kotlin-coroutines](https://www.baeldung.com/kotlin/spring-boot-kotlin-coroutines)
 *  (서버가 웹클라이언트 수행할때 코루틴 사용하는 법도 있으니 아래쪽 참고.. 단 3.x부터인듯)
 *
 *  - Reactive Web Flux는 아래와 같이 동작한다.
 *  > This raises the question: which thread is responsible for executing actual I/O operations?
 *    By default, each query’s operations run on a separate reactor NIO thread
 *    that’s chosen by an underlying scheduler implementation.
 *
 *  - Coruotine은 아래와 같이 동작한다.
 *  > database queries will run on the same reactor thread as in the reactive example.
 *      - 일반적인 시퀜셜로 코딩을 해도 무방하다.!!
 *      -
 *
 */
@RestController
@RequestMapping("/coroutine")
class TutorialController2 (private val tutorialService: TutorialService2) {

    /**
     * ### 비동기 + List완전 변환후 처리하는 경우 ::
     * >  5~7ms | Content-Type	application/json | Content-Length	259
     */
    @GetMapping("/tutorials")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllTutorials(@RequestParam(required = false) title: String?): List<Tutorial> {
        return tutorialService.findAll()
    }

    /**
     * ### 비동기 + LIST를 모아서 모노에심어서 데이터 전달하는 경우 :: 정상적인 Json으로 인식하지 못한다. : 아마 이건 못쓸듯
     * > 4~7ms | transfer-encoding	chunked  | Content-Type	text/event-stream;charset=UTF-8
     *
     *
     */
    @GetMapping("/tutorials.title/{title}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getTitleTutorials(@PathVariable title: String): Mono<List<Tutorial>> {
        return tutorialService.findByTitleContaining(title)
    }

    /**
     * ### 비동기 + 플럭스
     * > 3.5~7ms | transfer-encoding	chunked  | Content-Type	application/json
     *
     * ### Flux를 Flow로 변경함
     *
     */
    @FlowPreview
    @GetMapping("/tutorials/published")
    @ResponseStatus(HttpStatus.OK)
    suspend fun findByPublished(): Flow<Tutorial> {
        return tutorialService.findByPublished(true)
    }


    @GetMapping("/tutorials/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getTutorialById(@PathVariable("id") id: Int): Tutorial? {
        return tutorialService.findById(id)
    }



    @PostMapping("/tutorials")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createTutorial(@RequestBody tutorial: Tutorial): Mono<Tutorial> {
        return tutorialService.save(Tutorial(tutorial.title, tutorial.description, false))
    }

    @PutMapping("/tutorials/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun updateTutorial(@PathVariable("id") id: Int, @RequestBody tutorial: Tutorial?): Mono<Tutorial> {
        return tutorialService.update(id, tutorial!!)
    }

    @DeleteMapping("/tutorials/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteTutorial(@PathVariable("id") id: Int): Mono<Void> {
        return tutorialService.deleteById(id)
    }

    @DeleteMapping("/tutorials")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteAllTutorials(): Mono<Void> {
        return tutorialService.deleteAll()
    }

}