package arrow

import arrow.core.*
import kotlin.test.Test


/**
 * ### Arrow에 대한 내생각
 * > 일반적인 생성 값을 reactive 하게 처리 하기 위함인듯 하다..
 * > Either나 Option에 map Flatmap을 지원하는이유가 그래서 인듯
 */
class `03-ErrorHandingWithOption&Either` {


    //Convert Operation Logics
    //We wrap the parse result into an Option. Then, we’ll transform this initial value with some custom logic:
    fun isEven(x : Int) : Boolean  {
        return (x % 2) == 0
    }
    val isOdd : (x : Int) -> Boolean = {x -> x % 2 ==1}
    fun biggestDivisor(x: Int) : Int {
        return 10
    }
    fun isSquareNumber(x : Int) : Boolean {
        return true
    }


    @Test
    fun exampleOptionErrorHandling(){
        //First, we parse the input String as an integer.
        //Fortunately, Kotlin has a handy, exception-safe method:
        fun parseInput(s : String) : Option<Int> = Option.fromNullable(s.toIntOrNull())

        /**
         * > Let’s see how a client can work with the result:
         */
        fun computeWithOption(input : String) : Option<Boolean> {
            return parseInput(input)
                .filter(){x -> isEven(x)}
                .map(){x->biggestDivisor(x)}
                .map(){x->isSquareNumber(x)}
        }

        fun computeWithOptionClient(input : String) : String {
            val computeOption = computeWithOption(input)
            return when(computeOption) {
                is None -> "Not an even number!"
                is Some -> "The greatest divisor is square number: ${computeOption.getOrNull()}"
            }
        }
    }


    //위 내용을 Eather로 구현한다면...
    sealed class ComputeProblem {   //condition Factory
        data object OddNumber : ComputeProblem()
        data object NotANumber : ComputeProblem()
    }

    private fun parseInput(s : String) : Either<ComputeProblem, Int> =
        if(s.toIntOrNull() != null) Either.Right(s.toInt()) else Either.Left(ComputeProblem.NotANumber)

    fun computeWithEither(input : String) : Either<ComputeProblem, Boolean> {
        return parseInput(input)
            .filterOrElse(::isEven) { -> ComputeProblem.OddNumber }
            .map (::biggestDivisor)
            .map (::isSquareNumber)
    }

    fun computeWithEitherClient(input : String) {
        val computeWithEither = computeWithEither(input)
        when(computeWithEither) {
            is Either.Right -> "The greatest divisor is square number: ${computeWithEither.right()}"
            is Either.Left -> when(computeWithEither.getOrElse { computeProblem -> computeProblem  }) {
                is ComputeProblem.NotANumber -> "Wrong input! Not a number!"
                is ComputeProblem.OddNumber -> "It is an odd number!"
            }
        }
    }
}


