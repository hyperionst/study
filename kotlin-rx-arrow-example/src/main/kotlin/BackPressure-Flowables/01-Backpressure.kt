
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


fun main(args: Array<String>) {
    val observable = Observable.just(1,2,3,4,5,6,7,8,9)//(1)
    val subject = BehaviorSubject.create<Int>()

    //느린구독1
    subject.observeOn(Schedulers.computation())//(2)   Schedulers.computation() 계산을 수행할 쓰레드를제공한다 // 나중에 자세히 나온다
        .subscribe({//(3)
            println("Subs 1 Received $it")
            runBlocking { delay(200) }//(4)
        })

    //구독2
    subject.observeOn(Schedulers.computation())//(5)
        .subscribe({//(6)
            println("Subs 2 Received $it")
        })
    observable.subscribe(subject)//(7)


    //종료 막기 코드
    runBlocking { delay(2000) }//(8)
}
