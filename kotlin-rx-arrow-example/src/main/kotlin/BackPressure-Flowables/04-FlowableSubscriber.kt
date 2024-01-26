package chapter04.`BackPressure-Flowables`

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

fun main(args: Array<String>) {
    Flowable.range(1, 500)//(1)
        .map { MyItem5(it) }//(2)
        .observeOn(Schedulers.io())
        //Subscriber는 flowalbe과 backpressure 소통을 지원한다.
        .subscribe(object : Subscriber<MyItem5> {//(3)         //Subscriber 익명 내부 클래스 구현체 :


            override fun onSubscribe(subscription: Subscription) {
                subscription.request(Long.MAX_VALUE)//(4)          //전체를 받는것으로 설정한다.
            }

            override fun onNext(s: MyItem5?) {
                runBlocking { delay(50) }
                println("Subscriber received " + s!!)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

            override fun onComplete() {
                println("Done!")
            }
        })

    //강종 막기
    runBlocking { delay(60000) }
}

data class MyItem5(val id: Int) {
    init {
        println("MyItem Created $id")
    }
}
