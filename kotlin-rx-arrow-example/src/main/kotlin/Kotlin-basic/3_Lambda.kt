package `Kotlin-basic`
/*
* 2024.010.10 joyce강의 2강
* https://www.youtube.com/watch?v=Q5noYbbc9uc&t=515s 
*
* 1. Lambda
* # 람다식은 value 처럼 다룰수 있는 익명 함수를 뜻한다.
* - 메소드의 파라미터로 사용할수 있다.
* - 리턴값에 사용이 가능
* - 기존 클래스의 구현체에 대해서 확장함수의 선언이 가능하다.
*
*
* # 람다의 사용 형식
* val lambdaName : Type(intpt -> output (생략시타입유추)) = {argumentList(Type에서 생략했다면 타입을 적어줘야함) -> codeBody(마지막줄이 자동으로 리턴문}
*
* # 람다의 리턴 : 람다 바디안에 마지막 한줄이 리턴을 의미한다.
*
*
* #람다의 표현식은 다양하게 구성될수 있다.
*
*
* # android에서 button에 onClickListener를 연결하는 경우를 람다식으로 바로 압축표현이 가능하다.
*  (단 조건이 아래와같이 있다.)
* - 코틀린 인터페이스가 아니라 자바 인터페이스를 사용하는 경우여야한다.
* - 그 인터페이스 안에 딱하나의 메소드만 존재한다면... 아래와같이 표현해도 무방하다.
*  button.setOnClickListener {
*       //to do... sth
*  }
* */

val square : (Int) -> Int = {number -> number * number}   //앞에 선언할꺼면 입력타입은 반드시 괄호가 존재해야한다.
val square2 = {number : Int -> number * number}     //둘중한군대는 타입을 명시 해줘야 타입추론이 가능하다.

//람다를 선언할때는  반드시 타입추론이 잘되는지 확인을 하면서 진행하자
val nameAge ={name:String, age: Int ->  
    "my name ${name} Im ${age}"
}

fun main() {
    println(square(12))
    println(nameAge("joyce", 22))

    val a = "joyce said "
    val b = "mac said "
    println(a.pizzaIsGreat())
    println(b.pizzaIsGreat())
    println(extendString("ariana", 23))
    println(calculateGrade(90))

    
    //선언한 람다를 람다식을 파라메터로하는 함수에 넣어보자
    val lambda = {number : Double ->
        number > 6.14
    }
    println(invokeLambda(lambda))

    //여기서부터는 모두 같은 콜명령어이다.
    println(invokeLambda({ number -> number > 3.14}))   //람다 리터털을 넘겨서 처리하기  (파라미터가 1개이므로 it을 쓸수있다.)
    println(invokeLambda({it > 3.14}))   //it으로 변환
    println(invokeLambda(){ it > 3.14 }) //구지 괄호가 의미 있나?
    println(invokeLambda{ it > 3.14 })   //다빼자.
}


//확장함수  : 기존 클래스에 특정 함수를 추가하고 싶다면 확장 함수를 이용해서 바로 추가가 가능하다
var pizzaIsGreat : String.() -> String = {
    println(this.hashCode())
    val garasade ="pizza is the best"
    this + garasade     //여기서  this는 String Object를 나타낸다 (마지막 줄이 무조건 리턴인가?)
}


fun extendString(name: String, age: Int) : String {
    val introduceMyself : String.(Int) -> String = {"im ${this} and ${it} years old"}  //input parameter가 1개인경우 it키워드를 통해 바로 받을수 있다.
    return name.introduceMyself(age)
}


val calculateGrade : (Int) -> String = {  //어떤 경우에도 반드시 리턴이 존재하도록 구성해줘야한다.
    when(it) {
        in 0..40 -> "fail"
        in 41..70 -> "pass"
        in 71..100 -> "perpect"
        else -> "error"
    }
}

//람다의 여러가지 표현식
fun invokeLambda(lambda : (Double)-> Boolean) : Boolean{
    return lambda(5.2343)
}



