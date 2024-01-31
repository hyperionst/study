package `Kotlin-basic`

import java.util.Scanner
import kotlin.random.Random

/*
* 2024.01.11
* 오준석 생존코딩  : 코틀린 문법 총정리
* https://www.youtube.com/watch?v=OtHkb6wAI5U
*
*
* */

//컴파일 타임 상수   //class선언할 필요없이 변수 지정이 가능하다.
const val num = 20   //난 main보다 생성순위가 위다.

fun main() {

    val name = "123456789"


    //Random--------------------------------------
    val randomNumber = Random.nextInt(0,100)  //0~99
    val randomNumberD = Random.nextDouble(0.0,1.1)  //0~99
    println(randomNumber)


    //scanner input 받기---------------------------
    val reader = Scanner(System.`in`)   //  이미 kotlin에서 in이라는 키워드가 있기때문에 ``로 감싸줘야한다.
    reader.next()       // 스트링 받기~


    //예외처리 :: 자바와 같다.----------------------
    try {

    }catch (e: Exception){
        println(e)
    }finally {

    }
}

//--------------------class---------------------------
class person(
    private val name: String,   // 이렇게 하면 아에 접근도 되지 않는다.
    val jjj: Int,               //젤끝에 컴마를 넣어도 에러가 나지 않아요~~
                                //get / set은 별도 구현할 필요가 없으요
){
    //getter의 재정의는 좀 다르게 하는듯 하다.
    //

    fun voidFunction(){
        // to do
    }
}

//추상클래스  :: 클래스 자체에는 Open 이 필요업지만 추상 클래스 안에 어떤 함수를 재정의 해야한다면 open해줘야한다.
abstract class Animal {
    open fun move (){
        println("이동")
    }
    abstract fun sound()
}
class Cat : Animal(), Drawable {   //인터페이스는 괄호없이사용, 클래스확장은 ()가 필요함
    override fun move(){
        println("살금")
    }
    override fun sound(){
        println("야옹")
    }

    override fun draw() {

    }
}

//인터페이스----------------------------------------------
interface Drawable {
    fun draw()      //내용이 없는 함수는 자동으로 abstract로 판단된다.
    fun eat(){      //코틀린에서는 내용이 있는 함수를 인터페이스에 정의가능하다. // w자동으로 open취급된다.
        println("EAT IT")
    }

}

//타입체크
fun typeCheck() {
    //아래와같이 상태를 구성한다.
    val cat = Cat()
    val animal : Animal = Cat()

    //아래와 같이 타입체크가 가능하다.
    if (cat is Cat){
    }
    if (cat is Animal){
    }

}

//Generic class---------------------------------------------
class Box<T> (var value: T) {
    //이런식으로 선언한다.
}


//call back 함수
fun myFunc(callback: ()->Unit) {  //인자없음, //리턴없음
    println("함수시작")
    callback()
    println("함수끝")
}

//call back 함수
fun myFunc2(a: Int, callback: ()->Unit) {  //인자없음, //리턴없음
    println("함수시작")
    callback()
    println("함수끝")
}


fun myFuncRunner() {   //callback함수    //인자가 없는 함수는 매개변수가 자리에서 분리되어 밖에 정의가 가능하다.
    //아래 3개는 모두 같은 호출내용이다.
    myFunc({ println("함수호출됨") })
    myFunc() { println("함수호출됨") }
    myFunc { println("함수호출됨") }

    //아래 2개는 가능하다.
    myFunc2(10) { println("함수호출됨") }
    myFunc2(2, { println("함수호출됨") })

//--에러코드--
//    mySuspendFunc(2){
//        println("서스팬드함수 호출")
//    }
}

//Suspend함수  :: 함수의 내용이 완전히 끝날때까지 기다리는 동기함수  // main안에서는 실행이 불가능 함을 유의.  //코루틴이나 다른 서스팬드 함수에서 호출가능
suspend fun mySuspendFunc(a: Int, callback: ()->Unit) {
    println("서스팬드함수시작")
    callback()
    println("서스팬드함수끝")
}







