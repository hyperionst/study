package chapter03.`Observable-Observer-Subject`

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    runBlocking {
        //100m당 하나씩 생성하는 퍼블리셔를 생성
        val observale: Observable<Long> = Observable.interval(100, TimeUnit.MILLISECONDS)//1

        //아래의 기능을 수행하는 서브스크라이버를 구성한다.
        val observer: Observer<Long> = object : Observer<Long> {

            lateinit var disposable: Disposable//2

            override fun onSubscribe(d: Disposable) {
                disposable = d//3
            }

            override fun onNext(item: Long) {
                println("Received $item")
                if(item>=10 && !disposable.isDisposed) {//4
                    disposable.dispose()//5      //   --> 서브스크라이브를 멈추시오~~
                    println("Disposed")
                }
            }

            override fun onError(e: Throwable) {
                println("Error ${e.message}")
            }

            override fun onComplete() {
                println("Complete")
            }

        }

        observale.subscribe(observer)
        delay(3000)//6   프로그램이 종료되면 안되~

    }
}