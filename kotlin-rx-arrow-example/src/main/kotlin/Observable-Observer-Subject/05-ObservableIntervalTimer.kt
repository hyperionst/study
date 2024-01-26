package chapter03.`Observable-Observer-Subject`


import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val observer: Observer<Any> = object : Observer<Any> {
        override fun onComplete() {
            println("All Completed")
        }

        override fun onNext(item: Any) {
            println("Next $item")
        }

        override fun onError(e: Throwable) {
            println("Error Occured ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("New Subscription ")
        }
    }//Create Observer

    Observable.range(1,3).subscribe(observer)//(1)
    Observable.empty<String>().subscribe(observer)//(2)
    println("just before RunBlock")

    runBlocking {
        println("RunBlock")
        Observable.interval(300, TimeUnit.MILLISECONDS).subscribe(observer)//(3)
        delay(1500)

        //Timer는 한번만 실행된다.
        Observable.timer(1000, TimeUnit.MILLISECONDS).subscribe(observer)//(4)
        delay(2000)
    }

}