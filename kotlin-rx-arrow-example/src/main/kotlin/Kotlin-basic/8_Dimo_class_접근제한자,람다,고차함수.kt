package `Kotlin-basic`


/*
* https://www.youtube.com/playlist?list=PLQdnHjXZyYadiw5aV3p6DwUdXV2bZuhlN
* */


//-----------------------------------------------------------------------------------------------------------
//##클래스 상속과 접근제한자의 관계
open class SuperClassAnimal(var name : String, var age : Int) {
    protected open var sss : String = "sss"    //변수는 protected를 그대로 사용한다.
    fun introudce(){
        println("animal $name, $age")
    }
}

//java의 protected class를 선언시 internal로 사용한다.
internal class Duck(name : String, age: Int) : SuperClassAnimal(name, age) {   //value-parameter? --> property를 선언하는게 아니다.  전달용일뿐..
    override var sss = "SSS"
}

class Duck2(name : String, age: Int) : SuperClassAnimal(name, age) {   //value-parameter?
}


//-----------------------------------------------------------------------------------------------------------
//###고차 함수 ::  인자에 함수의 입력값과 출력값을 정의 해주면 그형태의 입력과 출력을 가지는 모든 함수는 인자로 사용가능하다. (함수역시 일종의 변수다!!)
fun a (str: String) : Unit {
    println("a함수")

}
fun b (functionalArgument: (String)->Unit){   //String input Parameter를 가지고 Int 리턴을 주는 모든 함수는 여기를 통과한다.
    println("b함수")
    functionalArgument("a")       //받아온 펑션에 인자값을 넣어서 실행시킨다.
}

fun lambdaValue(){
    //이쪽이 변수의형식    //실제 변수의 내용  이라고 생각하면 쉽다.
    val c : (String) -> Unit = {str -> println("람다") }
    //물론 변수형 기술없이 타입추론에 맞겨도 된다.
    val d = {str : String -> println("람다") }


    b(::a)    //일반함수를 고차함수로 넘길때는 :: 이 필요하다.
    b(c)      //그냥 변수를 이용해서 람다 함수로 생성하면 연산자 없이 그대로 넣을수 있다.
    b(d)

}


//-----------------------------------------------------------------------------------------------------------
//람다의 Scope함수

//람다의 여러줄 선언~~ :: 마지막 구문이 리턴이된다.
//패러미터가 하나라면 it을 사용가능 // 여러개라면 일일이 써야함.

//스코프 함수는 (함수형 언어의 특징)
//클래스로 선언한 인스턴스를 스코프 함수에 전달하면 스코프 함수 내에서 사용가능하다.
//apply, run with also let 을 사용가능

// apply : 인스턴스 생성후 변수에 담기 전에 초기화 과정을 수행할때 많이 씀
// run   : appry와 비슷 하지만 마지막 구문이 결과값을 반환하도록 되어있다.
// with  : run과 같은 기능을 하지만 인스턴스를 Parameter로 받는차이뿐.. a.run{...}  // with(a) {...}
// also(apply), let(run)  -->   스코프내에 같은 이름의 변수가 있을때 혼란을 방지하기 위함으로 it을 이용해서 parameter로 받은 인스턴스를 접근하도록 되어있다.

fun main(){

    val a = BookBook("bookname", 10000).apply {
        name = "[초특가]" + name;      //객체를 생성함과 동시에 특정동작을 수행시킬수 있다.  (기존이라면 a.name = 블라블라 a.discount를 따로 정리했겠지만...)
        discount()
    }

    val price = BookBook("bookname", 10000).run {
        name = "[초특가]" + name;      //객체를 생성함과 동시에 특정동작을 수행시킬수 있다.  (기존이라면 a.name = 블라블라 a.discount를 따로 정리했겠지만...)
        discount()
        price                     // run 은 마지막 구문이 리턴이다~
    }
    println(price)

    val price2 = with(a){
        println("with는 run하고 사용법이 다를뿐 하는 일은 같아요~")
        price
    }

    val price3 =  BookBook("bookname", 10000).also {
        it.discount()     //반드시 it을 통해서 접근 가능하다.
    }
}

class BookBook(var name: String, var price: Int){
    fun discount () {
        price -= 2000
    }
}


