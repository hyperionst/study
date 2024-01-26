package chapter07.concurrent

import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        .toObservable()
        .map { item ->
            println("Mapping $item - ${Thread.currentThread().name}")
            item.toInt()
        }
        .subscribeOn(Schedulers.computation())//(1)     전체 구독및 발행에 대한 쓰레드풀정책을 결정한다.
        .subscribe { item ->
            println("Received $item - ${Thread.currentThread().name}")
        }

    runBlocking { delay(1000) }
}