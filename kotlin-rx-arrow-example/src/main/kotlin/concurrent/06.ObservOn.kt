package chapter07.concurrent

import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


//연산자별로 다르게 쓰레드를 지정하고 싶다면 observeOn을 이용한다.
fun main(args: Array<String>) {
    listOf("1","2","3","4","5","6","7","8","9","10")
        .toObservable()
        .observeOn(Schedulers.computation())//(1)   //map의 경우 컴퓨테이션정책을 따른다.
        .map {
                item->
            println("Mapping $item - ${Thread.currentThread().name}")
            return@map item.toInt()
        }
        .observeOn(Schedulers.io())//(2)    아레쪽 연산자의 경우 IO스케줄려 정책을 따른다.
        .subscribe {
                item -> println("Received $item - ${Thread.currentThread().name}")
        }

    runBlocking { delay(1000) }
}