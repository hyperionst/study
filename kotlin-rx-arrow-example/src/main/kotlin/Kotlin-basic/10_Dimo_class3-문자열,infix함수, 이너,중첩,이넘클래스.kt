package `Kotlin-basic`/*
* 21강부터
*
*
* */


//-----------------------------------------------------------------------------------------------------------
//문자열
fun stringTest() {
    val test1 = "Test.Kotlin.String"

    println(test1.length)

    val test2 = test1.split(".")    //Delimiter로 나누기
    println(test2.joinToString())               //문저열을 합친다.
    println(test2.joinToString("-"))  //문자열에 -를 넣어서 합친다.

    println(test1.substring(5..10))

    println()

    val nullString: String? = null
    val emptyString = ""
    val blankString = " "
    val normalString = "A"

    println(nullString.isNullOrEmpty())
    println(emptyString.isNullOrEmpty())
    println(blankString.isNullOrEmpty())
    println(normalString.isNullOrEmpty())

    println()

    println(nullString.isNullOrBlank())      //눈에 보이지 않는 블랭크나 케리지리턴등만 포함하고있으면
    println(emptyString.isNullOrBlank())
    println(blankString.isNullOrBlank())
    println(normalString.isNullOrBlank())

    println()
}

//-----------------------------------------------------------------------------------------------------------
//null check & 클래스의 동일성
// 널세이프 ?                       sample?.toUpperCase()                 --> 객체가 널이라면 뒤를 실행하지 말라
// 엘비스   ?:                      sample?:"default".toUpperCase()       --> 객체가 null이라면 오른쪽을 쓸테다
// Not Null Assertion  !!           sample!!.toUpperCase()           --> null이 아니라고 프로그래머가 보장한다 :  럼타임에서 null이 들어오면 N.P.E가 발생할것이다.


// 널세이프의 활용
//  sample?.run{         스코프 연산자와 합처서도 사용이 가능하다.
//      toUpperCase()
//      toUpperCase()
//  }

// ==(내용의동일성을 물어본다  // equals를 기반으로 동작하는듯), === (객체의 동일성을 물어본다)


//-----------------------------------------------------------------------------------------------------------
//vararg(variable number of arguments)   : 같은 자료형의 데이터를 갯수에 상관없이 패러미터로 받고 싶을때.  // 다른 파라메터가있다면 젤뒤에 넣어야한다.
fun sum(text: String, vararg numbers: Int) {
    //이렇게 받아온 파라메터는 List 처럼 사용이 가능
    var sum = 0
    for (n in numbers) {
        sum += n
    }
    println(sum)
}


//-----------------------------------------------------------------------------------------------------------
//infix함수   // 함수자체를 객체맴버메소드나 연산자처럼 쓸수 있도록 선언할수 있는 기능을 제공한다.
infix fun Int.multiply(x: Int): Int {
    return this * x
}

fun infixTest() {
    val a = 6 multiply 4        //함수명 앞에 있는것이 함수의 this자리가 된다.  연산자처럼 쓸수 있기도하고..
    val b = 6.multiply(4)       //그냥 함수 호출 형태로 써도 된다.
}


//-----------------------------------------------------------------------------------------------------------
//nested class   하나의 클래와 다른클래스가 기능적으로 강하게 연결되어있음을 알리기위한 기능 (외부클래스와는 그냥 별개의 클래스다. 연결이 되지 않는다)
//inner  class   inner키워드를 사용한다. 스스로는 객체를 생성하지 못하게 숨겨진다.  (외부클래스의 속성과 함수를 사용가능하다)

fun main() {
    //nested는 직접 생성이 가능하다.
    Outer.Nested().introduce()

    //이너클래스는 직접 생성아 안되니 outer를 생성하고 나서 생성해야한다.
    val outer = Outer()
    val inner = outer.Inner()

    inner.introduceInner()
    inner.introduceOuter()

    outer.text = "Chagned"
    inner.introduceOuter()

}

class Outer {
    var text = "Outer Class"

    class Nested {
        fun introduce() {
            println("Nested")
        }
    }

    inner class Inner {
        var text = "Inner Class"
        fun introduceInner() {
            println(text)
        }

        fun introduceOuter() {
            println(this@Outer.text)

        }
    }
}

//-----------------------------------------------------------------------------------------------------------
//Enum class
fun enumTest(){
    var state = State.SING
    println(state)    // ==> SING

    state = State.SLEEP
    println(state.isSleeping())    //==> sleep

    state = State.EAT
    println(state.massage)     //  ==> eat sth
}


enum class State(val massage : String) {     // 열거형 타입에 대한 상태를 지정할때는 그 타입을 생성자에 타입을 선언해서 정의한다.)
    SING("sing a song"),
    EAT("eat sth"),
    SLEEP("sleep well");            //열거형의 마지막 내용에는 ;으로 끝임을 알려줘야한다

    //내장 함수를 선언도 가능하다
    fun isSleeping() : Boolean {
        return this == SLEEP
    }
}