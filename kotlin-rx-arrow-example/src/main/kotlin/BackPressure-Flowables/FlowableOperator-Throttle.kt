package chapter04.`BackPressure-Flowables`

import io.reactivex.Flowable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

//앞의 Buffer와 Window가 모아보낸다고 한다면... Throttle은 무시한다.~
fun main(args: Array<String>) {
    val flowable = Flowable.interval(100, TimeUnit.MILLISECONDS)//(1)
    flowable.throttleFirst(200, TimeUnit.MILLISECONDS)//(2)
        .subscribe { println(it) }

    runBlocking { delay(1000) }
}