package `chapter05-06`.ReactiveStreamFunctions

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

fun main() {
    println("---------------StartWith---------------")
    exampleStartWith()
    println("---------------ZIP---------------")
    exampleZip()
    exampleZipWith()

    println("---------------combineLatest & zip1---------------")
    exampleZipInterval()
    println("---------------combineLatest & zip2---------------")
    exampleCombineLatestInterval()


}

//스트림앞에 데이터를 붙여서 다운스트림으로 내보낸다.
fun exampleStartWith() {
    Observable.range(5, 10)
        .startWith(listOf(1, 2, 3, 4))   //startWith는 return은 Observable이지만 인자는 literable이다..
        .subscribe {
            println(it)
        }
}

//2개의 스트림을 기반으로 각각을 맵핑해서 연산을 수행하고 그 결과값을 다운스트림으로 내보낸다.
//여러 스트림을 사용했다면 길이가 가장 짧은 스트림(onError, onComplete)을 기준으로 zip을 수행, 나머지는 폐기된다.
//여러 스트림을 사용했다면 스트림마다 자기 번째의 데이터를 배출할때까지 기다렸다가 해당번째 연산을 수행한다.
fun exampleZip() {
    val observable1: Observable<Int> = Observable.range(1, 10)
    val observable2: Observable<Int> = Observable.range(11, 20)
    val observable3: Observable<Int> = Observable.range(21, 8)

    //Zip은 Observable의 Companion object다. Static way로 호출이 가능하다.
    //param: (프로듀서, ...프로듀서, multifunction(T,..T,->T)로 사용했다.
    Observable.zip(observable1, observable2, observable3, { emit1: Int, emit2: Int, emit3: Int ->
        run {
            emit1 + emit2 + emit3
        }
    }).subscribe {
        println("Recv : $it")
    }
}

//zip의 Object구현체 : 다른 프로듀서 하나만 처리할수 있음에 주의하자.
//여러 프로듀서를 동시에 처리해야하는경우 Observable.zip을 이용하자
fun exampleZipWith() {
    val observable1: Observable<Int> = Observable.range(1, 10)
    val observable2 = listOf(
        "String 1", "String 2", "String 3", "String 4",
        "String 5", "String 6", "String 7", "String 8", "String 9", "String 10"
    ).toObservable()

    //param : (프로듀서, Bifunction(T,T,->T))
    observable1.zipWith(observable2) { emit1: Int, emit2:String ->
        run{"$emit1 + $emit2"}
    }.subscribe { println(it) }
}

//여러스트림의 n번째 데이터가 모두 나올때까지 기다렸다가 연산을 수행
fun exampleZipInterval(){
    val observable1 = Observable.interval(100, TimeUnit.MILLISECONDS)//(1)
    val observable2 = Observable.interval(250, TimeUnit.MILLISECONDS)//(2)
    Observable.zip(observable1,observable2,
        { t1:Long, t2:Long -> "t1: $t1, t2: $t2" })//(3)
        .subscribe{
            println("ZIP Received $it")
        }

    runBlocking { delay(1100) }
}


//여러스트림에서 어느 한쪽이라도 데이터가 발생하면 합성을 하고 다운스트림으로 보낸다.
fun exampleCombineLatestInterval(){
    val observable1 = Observable.interval(100, TimeUnit.MILLISECONDS)//(1)
    val observable2 = Observable.interval(250, TimeUnit.MILLISECONDS)//(2)
    Observable.combineLatest(observable1,observable2,
        { t1:Long, t2:Long -> "t1: $t1, t2: $t2" })//(3)
        .subscribe{
            println("CL Received $it")
        }

    runBlocking { delay(1100) }
}









