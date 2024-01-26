package chapter04.`BackPressure-Flowables`

import io.reactivex.schedulers.Schedulers
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.toFlowable
import io.reactivex.rxkotlin.toObservable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


fun main(args: Array<String>) {
    val flowable = listOf("String 1","String 2","String 3","String 4","String 5").toFlowable()//(1)

    val processor = PublishProcessor.create<String>()//(2)

    //
    processor.//(3)
    subscribe({
        println("Subscription 1: $it")
        runBlocking { delay(1000) }
        println("Subscription 1 delay")
    })

    processor//(4)
        .subscribe({ println("Subscription 2 $it")})

    //여러 프로세서가 동시에 구독하게 된다면 이번 차례 데이터를 모든 프로세서가 수신받았다고 보장하기전에는
    //플로어블에서 새로운 데이터를 생성해내지 않는다.
    flowable.subscribe(processor)//(5)
}
