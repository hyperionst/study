package chapter07.concurrent

import io.reactivex.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    Observable.range(1,10)
        .subscribe {
            runBlocking { delay(200) }
            println("ob1 recved $it")
        }

    Observable.range(21,10)
        .subscribe {
            runBlocking { delay(100) }
            println("ob2 recved $it")
        }
}