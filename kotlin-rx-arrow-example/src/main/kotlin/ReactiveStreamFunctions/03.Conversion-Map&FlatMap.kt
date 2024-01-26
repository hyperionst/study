package `chapter05-06`.ReactiveStreamFunctions

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.rxkotlin.toObservable


/**
 *&copy; &  &uml; &trade; &iexcl; &pound;
 * &amp; &lt; &gt; &yen; &euro; &reg; &plusmn; &para; &sect; &brvbar; &macr; &laquo; &middot;
 *
 *#### Unordered Lists (-)
 * - List One
 * - List Two
 * - List Three
 *
 * #### Ordered Lists (-)
 * 1. First Line
 * 2. Second Line
 * 3. Third Line
 *
 * ## [Heading link](https://www.wikipedia.org/ "Heading link")
 * [Link](http://localhost/)
 */
fun main() {
    println("---------------map---------------")
    exampleMap()
    println("---------------flatMap1---------------")
    exampleFlatMap1()
    println("---------------flatMap2---------------")
    exampleFlatMap2()
}

//넘어온 요소들에 대한 연산을 수행하고 다운스트림으로 전달해준다.
fun exampleMap() {
    val observer = listOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1).toObservable()
    observer.map { num: Int ->
        run {  //다중실행시에는 run을 붙여서 쓰시오!!
            val stringNum: String = num.toString()
            "Transformed ${stringNum}"
        }
    }.subscribe { item ->
        println("recvd : $item")}
}

//넘어온 요소에 대해서 연산을 수행하고, 그 결과를 담고 있는 또다른 프로듀서를 생성해서 넘겨준다.
//데이터구조를 평평화(flat) 시키는 역활을 할수 있다고 하는데...
//인텔리제이에서 타입추론을 자세히 살펴봐야할듯 하다.
fun exampleFlatMap1(){
    val observer = listOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1).toObservable()
    observer.flatMap { number: Int ->
        Observable.just("flatmapped $number")  // 반환타입이 문자열을 포함한 옵저버블이다.
    }.subscribe {
        item: String -> println("Recvd : $item")
    }
}

//flatmap을 통과 해서 단일스트림에서 연산을 통해 생성되는 결과물들을 전체 모아서 새로운 다운스트림으로 구성한다.
fun exampleFlatMap2(){
    val observer = listOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1).toObservable()
    observer.flatMap { number: Int ->
        Observable.create<String> { emit : ObservableEmitter<String> -> run {
            emit.onNext("theNumber: $number")
            emit.onNext("theNumber + 1: ${number+1}")
            emit.onComplete()
        }}
    }.subscribe {
            item: String -> println("Recvd : $item")
    }
}

