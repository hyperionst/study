package chapter07.concurrent

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


fun main() {
    Observable.range(1,10)
        .subscribeOn(Schedulers.computation())    //스케쥴러(쓰레드풀)에 태운다.
        .subscribe {
            runBlocking { delay(200) }
            println("ob1 recved $it")
        }

    Observable.range(21,10)
        .subscribeOn(Schedulers.computation())    //스케쥴러(쓰레드풀)에 태운다.
        .subscribe {
            runBlocking { delay(100) }
            println("ob2 recved $it")
        }

    runBlocking { delay(2100) }
}