package chapter04.`BackPressure-Flowables`

import io.reactivex.Flowable

//window : 기본적으로 Buffer와 비슷하게 동작하지만... 컬랙션 형태가 아닌 프로듀서 형태로
//버퍼링한다.  --> 컬랙션이 아니므로 바로 람다로 받아서 다시 서브스크라이브 하는 형태를 취한다.
fun main(args: Array<String>) {
    val flowable = Flowable.range(1, 111)//(1)
    flowable.window(10)
        .subscribe { flo ->
            flo.subscribe { print("$it, ") }
            println()
        }
}