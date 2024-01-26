package com.hyperion.webfluxkotlin.router

import com.hyperion.webfluxkotlin.handler.BaseHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router


/**
 * # Html rest 요청의 엔트리 포인트 정리
 *  - routerFunction을 통해 경로를 지정하며 Bean 등록을 통해 모든 구성 클래스 내에서 생성 및 주입 할 수 있다.
 *  - Router는 MVC 패턴에서 Controller 클래스 및 메소드에 @GetMapping 또는 @PostMapping과 같은 API endPoint를 지정한다.
 */
@Configuration
class BaseRouter(private val handler: BaseHandler) {

    @Bean
    fun routerFunction(): RouterFunction<ServerResponse> = router{
        "v1".nest {
            listOf(
                GET("/test", {req: ServerRequest -> handler.get(req)} ),
                GET("/retrieveAll", {req: ServerRequest -> handler.getAll(req)}),
                GET("/retrieve/{baseId}", {req: ServerRequest -> handler.findByBaseId(req)})
            )
        }
    }


}