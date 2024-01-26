package `chapter05-06`.ReactiveStreamFunctions

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable


// 이전결과값에 대해서 계속 누적 연산을 수행하면서 다운스트림에 내보내준다.(roll aggregator)
fun main(args: Array<String>) {
    Observable.range(1, 10)
        .scan { previousAccumulation, newEmission ->
            previousAccumulation + newEmission }//(1)
        .subscribe { println("Received $it") }

    listOf("String 1", "String 2", "String 3", "String 4")
        .toObservable()
        .scan { previousAccumulation, newEmission ->
            previousAccumulation + " " + newEmission }//(2)
        .subscribe { println("Received $it") }

    Observable.range(1, 5)

        //연산을 수행하는 람다가 필요하다.!!
        .scan { previousAccumulation, newEmission ->
            previousAccumulation * 10 + newEmission }//(3)
        .subscribe { println("Received $it") }

}