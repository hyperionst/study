package `chapter05-06`.ReactiveStreamFunctions

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable


//Reducer는 유한한 데이터를 배출하는 스트림에 대해서만 동작함을 주의합시다.
//onComplete시점에서 데이터를 축소해서 내보내줄테니깐..

fun main() {
    println("---------------Count---------------")
    exmapleCount()
    println("---------------Reduce---------------")
    exampleReduce()

}

fun exmapleCount() {
    listOf(1, 5, 9, 7, 6, 4, 3, 2, 4, 6, 9).toObservable()
        .count()    //Return은 Single이다.
        .subscribeBy { println("count $it") }
}

fun exampleReduce() {
    Observable.range(1,10)
        .reduce{pre:Int, new: Int -> run{   //인자가 Bifunction(T,T -> T)이다.
            pre + new
        }}.subscribe { reduced : Int -> println(reduced) }

    Observable.range(1,5)
        .reduce{pre, new  ->
            pre*10 + new
        }.subscribe { reduced : Int -> println(reduced) }
}

