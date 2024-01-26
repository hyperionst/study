package `chapter05-06`.ReactiveStreamFunctions

import io.reactivex.Observable

fun main() {
    println("---------------default---------------")
    exampleDefaultEmpty()
    println("---------------swith---------------")
    exampleSwitchEmpty()

}

fun exampleDefaultEmpty() {
    Observable.range(0, 14)//(1)
        .filter { it > 15 }//(2)
        .defaultIfEmpty(15)//(3)   //조건에 부합하지 않아 비어있는 스트림에 기본값을 추가해줌
        .subscribe {
            println("Received $it")
        }
}

fun exampleSwitchEmpty() {
    Observable.range(0, 10)//(1)
        .filter { it > 15 }//(2)
        .switchIfEmpty(Observable.range(20, 10))//(3)   //조건에 부합하지 않아 비어있는 스트림대신 다른 스트림을 내보낸다.
        .subscribe {
            println("Received $it")
        }
}
