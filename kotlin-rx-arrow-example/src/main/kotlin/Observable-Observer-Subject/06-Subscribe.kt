package chapter03.`Observable-Observer-Subject`

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


fun main(args: Array<String>) {
    val observable: Observable<Int> = Observable.range(1,5)//1

    observable.subscribe({//2
        //onNext method
        println("SubScribe Next $it")
    },{
        //onError Method
        println("SubScribe Error ${it.message}")
    },{
        //onComplete Method
        println("SubScribe Done")
    })

    val observer: Observer<Int> = object : Observer<Int> {//3
    override fun onComplete() {
        println("All Completed")
    }

        override fun onNext(item: Int) {
            println("Next $item")
        }

        override fun onError(e: Throwable) {
            println("Error Occurred ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("New Subscription ")
        }
    }

    observable.subscribe(observer)


}