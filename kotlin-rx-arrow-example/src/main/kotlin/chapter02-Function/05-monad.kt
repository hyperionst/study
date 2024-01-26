package `chapter02-Function`

import io.reactivex.Maybe
import io.reactivex.rxkotlin.subscribeBy

fun main() {
    val maybeValue: Maybe<Int> = Maybe.just(14)
    maybeValue.subscribeBy(
        onError = {t  ->
            run {
                println("Error ${t}")
                println("Error ${t}")
            }
        },
        onComplete = { println("end with empty") },
        onSuccess = {value -> println("end with ${value}")},
    )
    val maybeEmpty:Maybe<Int> = Maybe.empty()//3
    maybeEmpty.subscribeBy(
        onComplete = {println("Completed Empty")},//4
        onError = {println("Error $it")},//5
        onSuccess = { println("Completed with value $it")}//6
    )
}