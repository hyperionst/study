package chapter03.`Observable-Observer-Subject`

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


//PublishSubject :  구독시점부터 계속해서 수신 받는다. // 구독전에 흘러간 데이터는 받을수 없다.
fun main(args: Array<String>) {
    val observable = Observable.interval(100, TimeUnit.MILLISECONDS)//1
    val subject = PublishSubject.create<Long>()//2
    observable.subscribe(subject)//3         구독등록
    subject.subscribe({l : Long -> run{//4    구독시 할일을 정의
        println("Received $l")
    }})
    runBlocking { delay(1100) }//5
}