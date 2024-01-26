package `chapter05-06`.ReactiveStreamFunctions

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    createObservable()//(1)
        .debounce(200, TimeUnit.MILLISECONDS)//(2)
        .subscribe {
            println(it)//(3)
        }

    println("-----------------distinct---------------")
    distinctExample()

    println("-----------------distinct---------------")
    distinctUntilChange()

    println("-----------------elementAt---------------")
    elementAtExample()

}


//200ms이상의 중간 시간이 있으면 그때 까지의 스트림을 처리한다.
//(즉 특정시간 입력이 없을때 중간까지의 입력을 내보내는구조)
fun createObservable(): Observable<String> = Observable.create<String> {
    it.onNext("R")//(4)
    runBlocking { delay(100) }//(5)
    it.onNext("Re")
    it.onNext("Reac")
    runBlocking { delay(130) }
    it.onNext("Reactiv")
    runBlocking { delay(140) }
    it.onNext("Reactive")
    runBlocking { delay(250) }   //(6) !!!
    it.onNext("Reactive P")
    runBlocking { delay(130) }
    it.onNext("Reactive Pro")
    runBlocking { delay(100) }
    it.onNext("Reactive Progra")
    runBlocking { delay(100) }
    it.onNext("Reactive Programming")
    runBlocking { delay(300) }   //!!!
    it.onNext("Reactive Programming in")
    runBlocking { delay(100) }
    it.onNext("Reactive Programming in Ko")
    runBlocking { delay(150) }
    it.onNext("Reactive Programming in Kotlin")
    runBlocking { delay(250) }  //!!!!
    it.onComplete()
}


//스트림내에서 중복된 값을 모두 버린다.
fun distinctExample() {
    listOf(1, 2, 2, 3, 4, 5, 5, 5, 6, 7, 8, 9, 3, 10)//(1)
        .toObservable()//(2)
        .distinct()//(3)
        .subscribe { println("Received $it") }//(4)   --> 뒤에서 두번째3은 나오지 않는다
}


//연속적인 중복값만을 제거한다.
fun distinctUntilChange() {
    listOf(1, 2, 2, 3, 4, 5, 5, 5, 6, 7, 8, 9, 3, 10)//(1)
        .toObservable()//(2)
        .distinctUntilChanged()//(3)
        .subscribe { println("Received $it") }//(4)  --> 뒤에서 두번째3역시 나온다.
}

fun elementAtExample() {
    val observable = listOf(10, 1, 2, 5, 8, 6, 9)
        .toObservable()

    //해당인덱스의 데이터 하나를 추출한다.
    observable.elementAt(5)//(1)
        .subscribe { println("Received $it") }

    //빈칸의 데이터를 참조한다면 공백이 나온다.
    observable.elementAt(50)//(2)
        .subscribe { println("Received $it") }
}

