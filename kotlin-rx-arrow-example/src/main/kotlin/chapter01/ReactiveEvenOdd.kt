package org.example.chapter01

import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

fun main() {
    var subject : Subject<Int> = PublishSubject.create()

    subject.map({ isEven(it) })
        .subscribe({
            println("this is ${(if (it) "even" else "odd")}")
        })
    subject.onNext(4)
    subject.onNext(9)
}


fun isEven(n:Int):Boolean = ((n % 2) == 0)