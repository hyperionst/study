package `chapter05-06`.ReactiveStreamFunctions

import io.reactivex.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit


// 여러 스트림을 동시 요청했을때 (예를들어서 여러디비에서 같은 데이터를 요청했을때..)
//먼저도착한 스트림을 이용하고 나머지 그후에 들어오는 스트림들은 무시한다.
//인자로 Iterable<Observable>의 구현체를 받는 것에 유의하자
fun main(args: Array<String>) {
    val observable1 = Observable.interval(500, TimeUnit.MILLISECONDS).map { "Observable 1 $it" }//(1)
    val observable2 = Observable.interval(100, TimeUnit.MILLISECONDS).map { "Observable 2 $it" }//(2)

    Observable
        .amb(listOf(observable1,observable2))//(3)
        .subscribe {
            println("Received $it")
        }

    runBlocking { delay(1500) }
}