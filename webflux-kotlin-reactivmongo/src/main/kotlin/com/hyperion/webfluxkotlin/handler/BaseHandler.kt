package com.hyperion.webfluxkotlin.handler

import com.hyperion.webfluxkotlin.entity.BaseDocument
import com.hyperion.webfluxkotlin.repository.BaseRepository
import com.hyperion.webfluxkotlin.service.BaseService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono


/**
 * handler 클래스는 ServerRequest에 따라 비지니스 로직 즉 Service Class 값 실행 및 ResposeEntity값을 생성해주는 역할을 한다.
 */
@Component
class BaseHandler(val repo: BaseRepository, val baseService: BaseService) {


    fun get(req: ServerRequest) : Mono<ServerResponse> {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body<String>(Mono.just("테스트 코드"))
    }


    fun getAll(req: ServerRequest) : Mono<ServerResponse> {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body<BaseDocument>(baseService.getAll())
            .switchIfEmpty(ServerResponse.notFound().build()).log("getALL")
    }

    /**
     *
     */
    fun findByBaseId(req: ServerRequest) : Mono<ServerResponse> {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body<BaseDocument>(baseService.getDataByBaseId(req.pathVariable("baseId")))
            .switchIfEmpty(ServerResponse.notFound().build())
    }

}