package `chapter05-06`.ReactiveStreamFunctions

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit


fun main() {
    println("---------------mergeWith---------------")
    exampleMergeWith()

    println("---------------mergeArray---------------")
    exampleMergeArray()

    println("---------------Merge (Ordering Async Case) ---------------")
    exmapleMerge()
}

//merge는 병렬작업에서는 순서 유지는 하지 않는다... 먼저 나온대로 그냥 튀어 나옴에 유의하라
//(정적으로 지정된 데이터라면 순서가 유지됨)
fun exmapleMerge() {
    val observable1 = Observable.interval(500, TimeUnit.MILLISECONDS).map { "[Observable 1] $it" }//(1)
    val observable2 = Observable.interval(100, TimeUnit.MILLISECONDS).map { "[Observable 2] $it" }//(2)

    Observable
        .merge(observable1, observable2)
        .subscribe {
            println("Received $it")
        }

    runBlocking { delay(1500) }
}

//merge는 최대 4개까지 인자를 받을수 있다.
fun exampleMergeArray() {
    val observable1 = listOf("A", "B", "C").toObservable()
    val observable2 = listOf("D", "E", "F", "G").toObservable()
    val observable3 = listOf("I", "J", "K", "L").toObservable()
    val observable4 = listOf("M", "N", "O", "P").toObservable()
    val observable5 = listOf("Q", "R", "S", "T").toObservable()
    val observable6 = listOf("U", "V", "W", "X").toObservable()
    val observable7 = listOf("Y", "Z").toObservable()

    Observable.mergeArray(
        observable1, observable2, observable3,
        observable4, observable5, observable6, observable7
    )
        .subscribe {
            println("Received $it")
        }
}

//Merge의 Object구현체
fun exampleMergeWith() {

    val observable1 = listOf("Kotlin", "Scala", "Groovy").toObservable()
    val observable2 = listOf("Python", "Java", "C++", "C").toObservable()

    observable1.mergeWith(observable2)
        .subscribe {
            println("Received $it")
        }
}

