package chapter04.`BackPressure-Flowables`

import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    val source = Observable.range(1, 1000)
    source.toFlowable(BackpressureStrategy.MISSING).onBackpressureLatest()
        .map { MyItem7(it) }
        .observeOn(Schedulers.io())
        .subscribe {
            print("Rec. $it;\t")
            runBlocking { delay(600) }
        }

    //
    runBlocking { delay(700000) }
}

