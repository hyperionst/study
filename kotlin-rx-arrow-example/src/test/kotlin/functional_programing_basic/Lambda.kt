package functional_programing_basic

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Lambda {

    @Test
    fun example(){
        //타입추론//
        val square = { number: Int -> number * number }
        val nine = square(3)

        val magnitude100String = { input : Int ->
            val magnitude = input * 100
            magnitude.toString()
        }


        //타입선언시에는...  인자는 반드시 괄호를 해줘야한다.//
        val that : (Int) -> Int = { three -> three }
        val more : (String, Int) -> String = { str, int -> str + int }
        val noReturn : (Int) -> Unit = { num -> println(num) }


        //Returning from a Lambda//
        val calculateGrade = { grade : Int ->
            when(grade) {
                in 0..40 -> "Fail"
                in 41..70 -> "Pass"
                in 71..100 -> "Distinction"
                else -> false
            }
        }

        // shortHand : it//
        val array = arrayOf(1, 2, 3, 4, 5, 6)
        array.forEach { item -> println(item) }
        array.forEach { println(it * 4) }       //인자가 하나일경우 그대로 사용하게 되므로 it으로 줄인다.


        //lambda parameter//
        fun invokeLambda(lambda: (Double) -> Boolean) : Boolean {
            return lambda(4.329)   //인자로 들어온 스테이트로 선언된 람다에 4.329를 넘겨준다.
        }
        val lambda = {arg : Double -> arg == 4.329}
        // invokeLambda {  }  //함수호출시 intelij는 이렇게 하는 옵션이 나타난다.
        invokeLambda(lambda)   //  선언된 람다를 함수에 인자로 전달
        invokeLambda { true }     // 람다 리터럴 ... 그냥 리턴값을 넣어버려도 동작을 하네 -0-
        val ref = {arg : Double-> arg.isFinite()}
        val ref2 = Double::isFinite  //람다 타입이지만// 타입이 KFunction으로 된다.
        invokeLambda(ref)   //  선언된 람다를 함수에 인자로 전달
        invokeLambda(ref2)   //  선언된 람다를 함수에 인자로 전달
        //
    }

    //callback 구성시 ::
    @Test
    fun givenMultipleMethods_whenCallingAnonymousFunction_thenTriggerSuccess() {
        //object로 선언된 전형적인 익명 내부 클래스~
        val result = Processor().performEvent(true, object : Processor.ActionCallback {
            override fun success() = "Success"
            override fun failure() = "Failure"
        })

        assertEquals("Success", result)
    }
}

class Processor {   //전형적인 콜백....
    interface ActionCallback {
        fun success() : String
        fun failure() : String
    }
    fun performEvent(decision: Boolean, callback : ActionCallback) : String {
        return if(decision) {
            callback.success()
        } else {
            callback.failure()
        }
    }
}

