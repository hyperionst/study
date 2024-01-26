package `chapter05-06`.ReactiveStreamFunctions

import io.reactivex.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.TimeUnit


fun main() {
    println("---------------flatmap---------------")
    caseFlatMap()

    runBlocking { delay(2000) }

    println("---------------concatMap---------------")
    caseConcatMap()
}




fun caseFlatMap() {
    Observable.range(1, 10)
        .flatMap {
            val randDelay = Random().nextInt(10)        //랜덤한 시간차를 줘서
            Observable.just(it).delay(randDelay.toLong(), TimeUnit.MILLISECONDS)//(1)  //그만큼 딜레이 시키로 던지는 구문인데..
            //flatmap이니깐  Observable 구현체로 리턴한다.
            //==> 즉 이전에 생성되어 수신받은 다운스트림의 순서가 바뀐채로 다시 다운스트림으로 보내는 구문이라고 이해하면된다.
        }
        .blockingSubscribe {
            println("Received $it")
        }
}

fun caseConcatMap() {
    Observable.range(1, 10)
        .concatMap {
            val randDelay = Random().nextInt(10)        //랜덤한 시간차를 줘서
            Observable.just(it).delay(randDelay.toLong(), TimeUnit.MILLISECONDS)//(1)  //그만큼 딜레이 시키로 던지는 구문인데..
            //flatmap이니깐  Observable 구현체로 리턴한다.
            //==> concatMap은 수신받은 순서를 기억하고 있다가 정리 되면 다운스트림으로 보낸다.
        }
        .blockingSubscribe {
            println("Received $it")
        }
}

