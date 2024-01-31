package arrow

import arrow.core.*
import com.hyperion.core.logger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue


/**
 * ### Some of the discussed data types here are Monads. Basically, Monads have the following properties:
 *
 *
 * - They are a special data type that is basically a wrapper around one or more raw values
 * - They have three public methods:
 *      + a factory method to wrap values
 *      + map
 *      + flatMap
 * - These methods act nicely, that is, they have no __side-effects__.
 * In the Java world, arrays and streams are Monads, but Optional isn’t.
 * For more about Monads, we could review this series explaining it progressively from ADTs to Monads.
 */
class `02-ArrowFunctionalDataType` {


    /**
     * ## Option :: None or Some
     * - Option is a data type to model a value that might not be present, similar to Java’s Optional.
     * - And while it __isn’t technically a Monad__, it’s still very helpful.
     * - It can contain two types: The Some wrapper around the value or None when it has no value.
     * > We have a few different ways to create an Option:
     *
     */
    @Test
    fun exampleOption() {
        //java Optional과 비슷하지만...
        val factory = Some(42)      //팩토리 방식 생성
        val constructor = Option(42)   //생성자 방식
        val emptyOptional: Option<Int> = none()    //none선언
        val fromNullable = Option.fromNullable(null)   //Static Way

        assertEquals(42, factory.getOrElse { -1 })
        assertEquals(factory, constructor)
        assertEquals(emptyOptional, fromNullable)


        //null safe관련 위배사항에 대한내용임
        val constructor2: Option<String?> = Option(null)     // 실제 값으로 들어간다. -> null safe하지 않다.
        val fromNullable2: Option<String?> = Option.fromNullable(null)  //
        try {
            logger.info { "first way is NOT null safe" }
            constructor2.map { s -> s!!.length }
        } catch (e: Exception) {
            logger.info { "Second way is null safe" }
            fromNullable2.map { s -> s!!.length }
        }
        assertNotEquals(constructor2, fromNullable2)
    }


    /**
     * ## Either :: Left(ERR) or Right(OK)
     * - Either goes further on this path and can have one of two values.
     * - Either has two generic parameters for the type of the two values, which are denoted as right and left:
     *      - This class is designed to be right-biased.
     *      - the right branch should contain the business value, say, the result of some computation.
     *      - The left branch can hold an error message or even an exception.
     * - 반드시 한쪽 bias는 비어있는 구조다
     * - 역시 map / flatmap을 사용가능하다.
     */
    @Test
    fun exampleEither(){
        val rightOnly : Either<String,Int> = Either.Right(42)    // Right쪽은 비지니스 value를 가진다.
        val leftOnly : Either<String,Int> = Either.Left("No Error")   // 처리결과에 대한 상태값을 가진다.

        assertTrue(rightOnly.isRight())
        assertTrue(leftOnly.isLeft())      //에러가있다면 이쪽이 true일것이다.
        // the value extractor method (getOrElse) is designed toward the right side:
        logger.info { leftOnly.getOrElse { -1 } }   //아무리 시도해도 right 값이 나오도록 되어있다.
        assertEquals(42, rightOnly.getOrElse { -1 })
        assertEquals(-1, leftOnly.getOrElse { -1 })

        //map and the flatMap methods are designed to work with the right side and skip the left side:
        assertEquals(0, rightOnly.map { it -> it % 2 }.getOrElse { -1 })
        assertEquals(-1, leftOnly.map { it % 2 }.getOrElse { -1 })
        assertTrue(rightOnly.flatMap { it -> Either.Right(it % 2) }.isRight())
        assertTrue(leftOnly.flatMap { Either.Right(it % 2) }.isLeft())
    }


    @Test
    fun exampleNonEmptyList(){
        val nelist1 =listOf(1, 2, 3, 4, 5).toNonEmptyListOrNull()
        println(nelist1)
        val nelist2 : NonEmptyList<Int>? =  emptyList<Int>().toNonEmptyListOrNull()
        println(nelist2?.get(1).toString())
    }





    //---------------------------------EVAL---------------------------------------------//
    //윽...
    /**
     * ## Eval
     * - Eval is a Monad designed to control the evaluation of operations.
     * - It has built-in support for memoization and eager and lazy evaluation.
     */
    @Test
    fun exampleEval() {
        //With the now factory method, we can create an Eval instance from already computed values:
        val now = Eval.now(1)
        var counter : Int = 0
        //The map and flatMap operations will be executed lazily:
        val map = now.map { x -> run {
                counter++
                counter
            }
        }
        logger.info { counter }
        logger.info{"---------------------------------"}
        val extract = map.value()
        logger.info { extract }
        logger.info { counter }
        logger.info { now.value() }
        logger.info{"---------------------------------"}
        val extract2 = map.value()
        logger.info { extract }
        logger.info { counter }
        logger.info { now.value() }
        logger.info{"---------------------------------"}
        val extract3 = map.value()
        logger.info { extract }
        logger.info { counter }
        logger.info { now.value() }
    }

    @Test
    fun exampleEval2(){
        var counter : Int = 0
        val later = Eval.later { counter++; counter }
        logger.info { counter}

        val firstValue = later.value()
        logger.info {firstValue}
        logger.info {counter }

        val secondValue = later.value()
        logger.info { secondValue }
        logger.info {counter }

        val secondValue2 = later.value()
        logger.info { secondValue2 }
        logger.info {counter }
    }

    @Test
    fun exampleEval3(){
        var counter : Int = 0
        val later = Eval.always { counter++; counter }
        logger.info {counter}

        val firstValue = later.value()
        logger.info {firstValue}
        logger.info { counter}

        val secondValue = later.value()
        logger.info {secondValue}
        logger.info {counter}

        val secondValue2 = later.value()
        logger.info {secondValue2}
        logger.info {counter}
    }
}


