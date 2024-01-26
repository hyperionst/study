package `chapter05-06`.ReactiveStreamFunctions

import io.reactivex.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

//concat은 순서를 유지해준다.(첫번째 스트림이 종료(onComplete, onError)되야만 두번째 스트림을 배출하기 시작한다.)
//concatWith, concatArray는 merge의 경우와 같다.
fun main(args: Array<String>) {
    val observable1 = Observable.interval(500, TimeUnit.MILLISECONDS)
        .take(2)//(1)   :: 2개쏘고 정지하도록 take를 추가했다... (안그럼 계속 observable1데이터만 나올것이다)
        .map { "[Observable 1] $it" }//(2)
    val observable2 = Observable.interval(100, TimeUnit.MILLISECONDS).map { "[Observable 2] $it" }//(3)

    Observable.concat(observable1,observable2)
        .subscribe {
            println("Received $it")
        }

    runBlocking { delay(1500) }
}


