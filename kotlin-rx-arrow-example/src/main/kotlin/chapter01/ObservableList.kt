package org.example.chapter01
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    var list:List<Any> = listOf("One", 2, "Three", "Four", 4.5, "Five", 6.0f) // 1
    var observable: Observable<Any> = list.toObservable(); //1

    //TIP : subscribeBy에 상황별 해야할일들을 구성한다.
    observable.subscribeBy (
        onNext = { a: Any -> println(a) },
        onError =  { t :Throwable -> t.printStackTrace() },
        onComplete = {println("Done!") }
    )

}


