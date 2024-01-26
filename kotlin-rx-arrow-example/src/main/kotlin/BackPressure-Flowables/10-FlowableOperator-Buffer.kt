package chapter04.`BackPressure-Flowables`

import io.reactivex.Flowable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit


//flowable.buffer()는 생산되는 flowalbe 아이템을 모아서 처리한다.

fun main(args: Array<String>) {
    var flowable = Flowable.range(1,111)//(1)
    flowable.buffer(10)
        .subscribe { println(it) }

    println("-----------------buffer skip---------------")
    flowable.buffer(10,15)//(1)   스킵이라는 말보다 스탭이 잘어울리듯 하다 15스탭씩 10개
        .subscribe { println("Subscription 1 $it") }

    flowable.buffer(15,7)//(2)   7스탭씩 15개가 출력된다.
        .subscribe { println("Subscription 2 $it") }

    println("-----------------buffer time---------------")
    val flowable2 = Flowable.interval(100, TimeUnit.MILLISECONDS)//1
    flowable2.buffer(1, TimeUnit.SECONDS)//(2)
        .subscribe { println(it) }

    runBlocking { delay(5000) }//(3)

}