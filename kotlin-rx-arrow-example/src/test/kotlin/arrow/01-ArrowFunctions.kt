package arrow

import arrow.core.compose
import arrow.core.curried
import arrow.core.flatten
import arrow.core.partially1
import com.hyperion.core.logger
import org.junit.jupiter.api.Test


class `01-ArrowFunctions`{
    //TIP Arrow provides us with a compose() extension function that
// we can use to create a new function that takes the result of the first one and applies the second one

    @Test fun exampleCompose() {
        val multiplyBy2 = { i: Int -> i * 2 }
        val add3 = { i: Int -> i + 3 }
        val composed = multiplyBy2 compose  add3
        val result = composed(4)  //
        logger.info { result }

    }

    // Another interesting function is curried(),
// which allows us to take multiple parameters and convert them to a chain of functions,
// taking only one parameter that returns a function taking one parameter;
// it makes our functions more flexible and allows us to apply parameters to them in a step by step manner:
    @Test fun exampleCurried(){
        val add = { a: Int, b: Int -> a + b }
        val curriedAdd = add.curried()
        val add2 = curriedAdd(2)
        val result = add2(3)  // Result: 5
        logger.info { result }
    }

    // partially() gives us the possibility to pass on some arguments to a function
    // without having to curry it first,
    // creating a function with fewer parameters but not converted to a chain of single parameter functions:
    @Test fun examplePatially(){
        val addThreeNums = { a: Int, b: Int, c: Int -> a + b + c }
        val add2 = addThreeNums.partially1(2)
        val result = add2(3, 4)  // Result: 9
        logger.info { result }
    }


    //
    @Test fun exampleFlatMap(){
        val myMap = mapOf(
            "a" to listOf(1, 2, 3),
            "b" to listOf(4, 5, 6),
            "c" to listOf(7, 8, 9)
        )

//    // Use flatMap to transform and flatten the Map
//    val flatMappedList = myMap.flatMap { entry ->
//        entry.value.map { value ->
//            "$${entry.key}$value"
//        }
//    }
//    println(flatMappedList)

    }


    // The flatten() function extends some containers (E.g. Iterable, Sequence),
    // with a function that enables us to simplify nested contexts into a single level:
    // 근데 코틀린 collection에서 이미 같은 기능이 있는거 같은데???
    @Test fun exampleFlatten(){
        val list = listOf(listOf(1, 2), listOf(3, 4))
        val result = list.flatten()  // Result: listOf(1, 2, 3, 4)
    }


}

