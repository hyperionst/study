package chapter03.`Observable-Observer-Subject`

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy


/**
 * Created by rivuc on 22-07-2017.
 */

fun main(args: Array<String>) {

    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("All Completed")
        }

        override fun onNext(item: String) {
            println("Next $item")
        }

        override fun onError(e: Throwable) {
            println("Error Occured ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("New Subscription ")
        }
    }//Create Observer

    val observable:Observable<String> = Observable.create<String> {//1
        //create를 통해 observable emitter를 생성하고 onNext를 통해서 데이터를 내보낸다.
        //임의로 데이터 구조를 내보낼 수 있다.
        it.onNext("Emit 1")
        it.onNext("Emit 2")
        it.onNext("Emit 3")
        it.onNext("Emit 4")
        it.onComplete()
    }

    observable.subscribe(observer)

    val observable2:Observable<String> = Observable.create<String> {//2
        it.onNext("Emit 1")
        it.onNext("Emit 2")
        it.onNext("Emit 3")
        it.onNext("Emit 4")
        it.onError(Exception("My Custom Exception"))
    }

    observable2.subscribe(observer)






}