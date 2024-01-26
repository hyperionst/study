package `Kotlin-basic`
/*
 * 2024.01.10  joyce강의 1강
 * https://www.youtube.com/watch?v=IDVnZPjRCYg
 */


// 파일이름과 클래스 이름이 똑같아야만 하지 않는다.
// 한 파일에 여러 클래스를 선언해도 된다.



open class Human constructor(val name: String="anonymous"){
    //kotlin initiator : 클래스 선언시 인자처럼 생성할수있다.  default도 사용가능/

    init{  //주 생성자 // Poperty는 자동으로 되지만 행동을 할려면 여기에 정의해야한다.
        //클래스가 생성될때 자동으러 실행한다.
        println("New human ${name}")
    }

    //생성자 오버로딩시 :: 부생성자인 Constructor를 사용해야한다. --> 사용시 필요한 추가정보는 : 이후 넣어주어야한다.
    constructor(name:String, age: Int) : this(name){
        println(" ${name} age ${age}")
    }

    fun eatCake(){
        println("this is yummyYYYY~~~")
    }
    open fun singASong(){
        println("lalala")
    }
}


class Korea : Human(){   //Kotlin class는 기본적으로 final이다. 상속을 할려면  superClass에 open을 명시해야한다.

    //상속구조에서의 오버라이딩 : override keyworld를 통해 명시 해줘야하며 super class의 메소드에서도 open이 되어야한다.
    override fun singASong(){
        println("라라라")
    }

}


//단일 상속만 된다.
fun main(){
    val human =  Human()     //클래스 생성시 NEW는 필요없다.
    human.eatCake()
    val human2 =  Human("jayce", 25)     // 주 생성자가 무조건 먼저 실행되고 부 생성자가 나중에 실행되는 구조로 초기화된다.

    println("--------------------Korea class-----------------------")
    val korea = Korea()
    korea.singASong()
}


//abstract class, data class
//Object, companion Object