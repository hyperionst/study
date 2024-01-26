package `Kotlin-basic`

/**
 * # Hello wordl project
 *  - 주석은 마크다운이라며?
 *  ```kotlin
 *      println("hello world")
 *      println("Have a nice day.")
 * ```
 *
 * ## 정말 markdown 인가?
 *
 *
 * `12345`
 * **1234**
 * *1234*
 * >1234
 *
 *
 *
 */
fun main() {
    println("hello world")
    println("Have a nice day.")


    print("Hello, World! ")
    print("Have a nice day.")




    //변수의 선언
    val valGreeting = "Hello, World!" // val은 한 번 지정하면 변경할 수 없는 변수   ==> Value
//    valGreeting = "Hi" --> ERROR
    var varGreeting = "Hello, World!" // var은 값을 업데이트할 수 있는 변수
    varGreeting = "Hi"


    //Nullable을 선언할땐
    var myValue: Int? = null // 자료형(Int) 다음에 물음표
    println(myValue)


    // 숫자 자료형(정수)
    var a1 = 1 // 10진수 정수 자료형 Int
    var a2 = 0xABCD // 16진수는 Int로 추론됨
    var a3 = 0b01010 // 2진수는 Int로 추론됨

    var a4 = 1L // Long

    // 숫자 자료형(실수)
    var b1 = 1.5 // Double
    var b2 = 1.5f // Float

    // 문자 자료형과 문자열 자료형
    var c1 = 'c' // 문자
    var c2 = "Hello, World!" // 문자열

    // 논리 자료형
    var d = true // 불린

    println("binary" + a3.toString())

}