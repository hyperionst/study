package functional_programing_basic

import com.hyperion.core.logger
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import functional_programing_basic.Answer.*




    val input = """
    T, T, T, T, T, T
    F, O, F, O, F, O
    T, F, O, F
    T, O, T, O, T, O
    T, X, T, X, T, X
    F, F, F, F, F, F
""".trimIndent()

    enum class Answer {
        True, False, Empty
    }

    val expectedResult = listOf(
        listOf(Answer.True, Answer.True, Answer.True, Answer.True, Answer.True, Answer.True),
        listOf(Answer.False, Answer.Empty, Answer.False, Answer.Empty, Answer.False, Answer.Empty),
        listOf(Answer.True, Answer.Empty, Answer.True, Answer.Empty, Answer.True, Answer.Empty),
        listOf(Answer.True, Answer.True, Answer.True),
        listOf(Answer.False, Answer.False, Answer.False, Answer.False, Answer.False, Answer.False),
    )


/**
 *
 * ## example of using return in lambda.
 *
 * In Kotlin, functions are first-class. Furthermore,
 * lambda expressions provide a concise and powerful way to express functionality.
 * These anonymous functions allow us to write more expressive code.
 *
 * However, using the return keyword inside a lambda might initially seem confusing
 * due to the nature of lambdas and their implicit return behavior.
 * In this tutorial, we’ll explore the return usage inside a lambda in Kotlin.
 * https://www.baeldung.com/kotlin/return-inside-a-lambda
 */
class ReturnAtLabelUnitTest {

    lateinit var resultList: MutableList<List<Answer>>

    fun processInputV1(input: String) {
        resultList = mutableListOf()
        input.lines().forEach { line: String ->
            val field = line.split(", ")
            if (field.size != 6) return   //이시점에서 통체로 리턴되버린다. 루프를 continue하는 느낌으로 넘겨야한다.
            val answerList: MutableList<Answer> = mutableListOf()
            field.forEach { eachValue : String ->
                answerList += when ( eachValue ) {
                    "T" -> True
                    "F" -> False
                    "O" -> Empty
                    else -> return
                }
            }
            resultList += answerList
        }
    }

    fun processInputV2(input: String) {
        resultList = mutableListOf()
        input.lines().forEach { line: String ->
            val field = line.split(", ")
            if (field.size != 6) return@forEach
            val answerList: MutableList<Answer> = mutableListOf()
            field.forEach { eachValue : String ->
                answerList += when ( eachValue ) {
                    "T" -> True
                    "F" -> False
                    "O" -> Empty
                    else -> return     //이시점에서 통체로 리턴되버린다. 루프를 continue하는 느낌으로 넘겨야한다.
                }
            }
            resultList += answerList
        }
    }

    fun processInputV3(input: String) {
        resultList = mutableListOf()
        input.lines().forEach { line: String ->
            val field = line.split(", ")
            if (field.size != 6) return@forEach
            val answerList: MutableList<Answer> = mutableListOf()
            field.forEach { eachValue : String ->
                answerList += when ( eachValue ) {
                    "T" -> True
                    "F" -> False
                    "O" -> Empty
                    else -> return@forEach   //이렇게 되면 되긴 하는데 이름이 같으니...애매허다
                }
            }
            resultList += answerList
        }
    }

    //람다 리턴 구문에 이름을 별도 부여하자
    fun processInputV4(input: String) {
        resultList = mutableListOf()
        input.lines().forEach lineProcessing@{ line: String ->
            val field = line.split(", ")
            if (field.size != 6) return@lineProcessing
            val answerList: MutableList<Answer> = mutableListOf()
            field.forEach answerProcess@{ eachValue : String ->
                answerList += when ( eachValue ) {
                    "T" -> True
                    "F" -> False
                    "O" -> Empty
                    else -> return@answerProcess
                }
            }
            resultList += answerList
        }
    }


    fun printResult() {
        logger.info(
            """
               |The Result After Processing:
               |----------------
               |${resultList.joinToString(separator = System.lineSeparator()) { "$it" }}
               |----------------
            """.trimMargin())
    }

    @Test
    fun `when using return directly in lambda, then exit the enclosing function`() {
        processInputV1(input)
        assertNotEquals(expectedResult, resultList)
        printResult()
    }

    @Test
    fun `when using return directly in the inner foreach lambda, then exit the enclosing function`() {
        processInputV2(input)
        assertNotEquals(expectedResult, resultList)
        printResult()
    }

    @Test
    fun `when using return with the default label in both foreach lambdas, then get the expected result`() {
        processInputV3(input)
        assertEquals(expectedResult, resultList)
        printResult()
    }

    @Test
    fun `when using return with custom labels in both foreach lambdas, then get the expected result`() {
//        processInputV4(input)
        assertEquals(expectedResult, resultList)
        printResult()
    }



}