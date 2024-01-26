package chapter03.`Observable-Observer-Subject`

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toObservable

fun main(args: Array<String>) {

    val observer: Observer<Any> = object : Observer<Any> {    //Observer인터페이스를 구현하는 익명 클래스다.
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
            println("New Subscription $d")
        }
    }//Create Observer

    //Observer(컨수머)의 구현기능이 끝났으니


    //옵저버러블에 붙여서 써보자.
    val list:List<String> = listOf("String 1","String 2","String 3","String 4")

    val observable: Observable<String> = list.toObservable()

    observable.subscribe(observer)


    val observableOnList: Observable<List<Any>> = Observable.just(listOf("One", 2, "Three", "Four", 4.5, "Five", 6.0f),
        listOf("List with Single Item"),
        listOf(1,2,3,4,5,6))//8

    observableOnList.subscribe(observer)//9
}