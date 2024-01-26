package `chapter05-06`.ReactiveStreamFunctions

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy


fun main() {
    println("---------------filter---------------")
    filterExample()
    println("---------------First&Last---------------")
    exampleFirstLast()
}


fun filterExample() {
    Observable.range(1, 20)//(1)
        .filter {//(2)
            it % 2 == 0    //여기 조건이 true인 것만 넘어간다.
        }
        .subscribe {
            println("Received $it")
        }
}

fun exampleFirstLast() {
    val observable = Observable.range(1, 10)
    observable.first(2)//(1)
        .subscribeBy { item -> println("Received $item") }

    observable.last(2)//(2)
        .subscribeBy { item -> println("Received $item ${item - 1}") }

    //조건의 데이터가 비어있다면 defaultItem을 배출한다.
    Observable.empty<Int>().first(2)//(3)
        .subscribeBy { item -> println("Received $item") }

    //Element를 모두 제외시키고 onComplete에만 관심이 있을때 사용한다.
    observable.ignoreElements()
        .subscribeBy(onError = { t -> println("Error $t") }, onComplete = { println("Complete") })

}