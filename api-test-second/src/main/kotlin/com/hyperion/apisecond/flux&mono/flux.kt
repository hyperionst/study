package com.hyperion.apisecond.`flux&mono`

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.util.function.Consumer


/**
 * simple flux example
 * Fulx : reactivX의 Flowable과 비슷하다. BackPressure를 지원한다.
 * Mono : Single & Maybe의 조합으로 사용됨
 * 기본적으로 모든 리액터 유형은 리액티브 스트림의 Publisher API를 직접 구현한다.
 */
fun main() {
    println("-------------Simple flux------------------")
    exampleSimpleFlux()
    println("-------------Simple Mono------------------")
    exampleMono()
}


fun exampleSimpleFlux() {
    val flux = Flux.just("item1", "item2", "item3")
    flux.log().subscribe(object : Consumer<String> {
        override fun accept(t: String) {
            println("Got Item: $t")
        }
    })
}


fun exampleMono(){
    val consumer : Consumer<String> = object : Consumer<String> {
        override fun accept(t: String) {
            println("Got Item: $t")
        }
    }

    val emptyMono = Mono.empty<String>()
    emptyMono.log().subscribe(consumer)

    val emptyMono2 = Mono.justOrEmpty<String>(null)
    emptyMono2.log().subscribe(consumer)

    val dataMono = Mono.justOrEmpty<String>("String")
    dataMono.log().subscribe(consumer)

    val monoExtend = "another String".toMono()
    monoExtend.log().subscribe(consumer)

}