package chapter03.`Observable-Observer-Subject`

import io.reactivex.subjects.AsyncSubject

//AsyncSubject : 구독이 끝나는 시점에서 마지막 데이터만 전달받는다.
fun main(args: Array<String>) {
    val subject = AsyncSubject.create<Int>()
    subject.onNext(1)     //Observable에 대한 구독없이 onNext직접 선언해서 데이터를 전달할수 있다.
    subject.onNext(2)
    subject.onNext(3)
    subject.onNext(4)

    println("1st sub")
    subject.subscribe({
        //onNext
        println("S1 Received $it")
    },{
        //onError
        it.printStackTrace()
    },{
        //onComplete
        println("S1 Complete")
    })

    println("next 5")
    subject.onNext(5)

    println("2nd sub")
    subject.subscribe({
        //onNext
        println("S2 Received $it")
    },{
        //onError
        it.printStackTrace()
    },{
        //onComplete
        println("S2 Complete")
    })


    subject.onComplete()//6     --> complete되는 시점에서 마지막 값을 수신 받는다.
}