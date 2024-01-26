package chapter04.`BackPressure-Flowables`

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

fun main(args: Array<String>) {
    Flowable.range(1, 15)
        //1~15까지 아이탬 생성
        .map { MyItem6(it) }  // 아이탬 생성을확인
        .observeOn(Schedulers.io())
        .subscribe(object : Subscriber<MyItem6> {  //Subscriber 구현체 작성
            lateinit var subscription: Subscription//(1)

            override fun onSubscribe(subscription: Subscription) {
                this.subscription = subscription
                subscription.request(5)//(2)       //첫 5개 데이터 요청
            }

            override fun onNext(s: MyItem6?) {
                runBlocking { delay(50) }
                println("Subscriber received " + s!!)
                if(s.id == 5) {//(3)
                    println("Requesting two more")
                    subscription.request(2)//(4)    //2개 추가요청
                }
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

            override fun onComplete() {      //음? 나오지 않는다.... (실제 데이터도 15개가 아닌 7개만 나온다)
                println("Done!")
            }
        })
    
    //종료 딜레이
    runBlocking { delay(10000) }
}

data class MyItem6 (val id:Int) {
    init {
        println("MyItem Created $id")
    }
}