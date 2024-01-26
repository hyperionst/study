package `chapter05-06`.ReactiveStreamFunctions

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy


fun main() {
    println("---------------return---------------")
    exampleReturn()
    println("---------------resumNext---------------")
    exampleResumeNext()

    println("---------------resumNext---------------")
    exampleRetry()

}

fun exampleReturn() {
    Observable.just(1,2,3,4,5)
        .map { it/(3-it) }              //0으로 나누는 케이스가 생긴다.
        .onErrorReturn { -1 }//(1)      //에러 발생시 -1을 반환하고 정지한다
        .subscribe {
            println("Received $it")
        }
}


//에러발생시 다른 프로듀서를 구독하도록 한다.
fun exampleResumeNext() {
    Observable.just(1,2,3,4,5)
        .map { it/(3-it) }
        .onErrorResumeNext(Observable.range(10,5))//(1)
        .subscribe {
            println("Received $it")
        }
}


fun exampleRetry(){
    Observable.just(1,2,3,4,5)
        .map { it/(3-it) }
        .retry(3)//(1)      3번 이용~~
        .subscribeBy (
            onNext  = {println("Received $it")},
            onError = {println("Error")}
        )
    println("\n With Predicate \n")

    var retryCount = 0
    Observable.just(1,2,3,4,5)
        .map { it/(3-it) }
        .retry {num:Int, T-> run{
            println("Error on $num")
            (++retryCount)<3
        }}
        .subscribeBy (
            onNext  = {println("Received $it")},
            onError = {println("Error")}
        )
}
