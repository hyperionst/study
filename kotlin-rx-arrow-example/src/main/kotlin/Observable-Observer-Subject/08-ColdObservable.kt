package chapter03.`Observable-Observer-Subject`

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable


//사실 앞에서 한 예제가 모두 Cold이다.
fun main(args: Array<String>) {
    val observable: Observable<String> = listOf("String 1","String 2","String 3","String 4").toObservable()//1

    observable.subscribe({//2
        println("ob1 Received $it")
    },{
        println("Error ${it.message}")
    },{
        println("ob1 Done")
    })

    observable.subscribe({//3
        println("ob2 Received $it")
    },{
        println("Error ${it.message}")
    },{
        println("ob2 Done")
    })

}