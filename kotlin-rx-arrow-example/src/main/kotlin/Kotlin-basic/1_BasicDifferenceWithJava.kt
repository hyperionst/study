package `Kotlin-basic`
/*
 * 2024.01.10  joyce강의 1강
 * https://www.youtube.com/watch?v=IDVnZPjRCYg
 */

fun main() {
    helloWorld()
    println(add(4,5))
    checkNumber(85)
    repeat()
    nullCheck()
    ignoreNull("string")


}

//1. Function-------------------------------------

//리턴형이 없는 함수 선언
fun helloWorld() : Unit {
     println("joyce hello")
    stringTemplate()
}
// 변수명을 앞에 타입을 뒤에 쓴다.s
fun add (a: Int, b: Int) : Int {
    return a+b
}

//2 val(value) vs var(variable)-------------------------------------
//타입명의 시작은 항상 대문자일듯 하다. // 원시타입이 지원되지 않기 때문이다.
fun hi() {
    val a : Int = 10
    var b : Int = 9
    b = 10
    //a = 11   불가능~~

    var name = "joyce"  //type 자동추론되므로... 그냥 생략해도 된다.
}


//3. Kotlin String Template-------------------------------------
fun stringTemplate() {
    val name = "joyce"
    val lastName = "jj"
    println("my name is ${name + lastName} imdd")    //Template 안에서 연산도 처리해서 나온다.
    println("is that true? ${1==0}")
    println("this is 2\$value")              //실제 달러표시가 문자일때는 \를 앞에쓰면 문자로인식해준다.

}


//4.조건식--------------------------------------------------

//일반적인 연산문을 포함한 함수
fun maxBy1(a: Int, b: Int) : Int {
    if (a > b) {
        return a
    } else {
        return b
    }
}

//3항연산자형태의 함수--------------------------------------------------
fun maxBy2(a: Int, b: Int) = if(a>b)a else b


//when --> Switch대응 조건문
fun checkNumber(score : Int ){
    when(score) {
        0 -> println(0)
        1 -> println(1)
        2,3 -> println(2)
        //조건절로 사용하고 있다면 else가 없어도 괜찮습니다.
    }

    //when을 통한 변수할당
    val valuedScore = when(score){
        0 -> "0"
        1 -> "1"
        2 -> "2"
        else -> "else"
    }
    println("when을 통한 변수할당 ${valuedScore}")

    when(score){
        in 90..100 -> println("A")
        in 10..80 -> print("soso")
        else -> println("okay")
    }
}
//참고::Expression & Statement의 차이점
// expression --> 뭔가의 작업을 통해서 값을 만들어 내는것  (코틀린의 모든 함수는 expression 이지요)
// statement  --> 제어문같이 어떠한 특정값을 만들어 내지않다. 조건절, 명령문 등등



//5. Array & List---------------------------------------------------

//List  -> Mutable & Immutable 두개로 나뉜다.
fun array() {
    val array = arrayOf(1,2,3)
    val list = listOf(1,2,3)

    //immutable List
    //any-type
    val array2 = arrayOf(1,2,"d",3.4f)
    val list2 = listOf(1,"d", 11L)
    array[0] = 2
    val a = list.get(0)
    val b = list[2]
    //list[0] = 2  --> 변경이 불가능하다. (list는 인터페이스 인데 세부내용을 보면 set관련 내용이 없다...)

    //mutable List의 대표적 사례
    val arrayList = arrayListOf<Int>(10)    //참조값이 변하지 않으므로 val로 선언하고도 내부 값 변경이 가능함을 조심. 실제 어레이리스트를 새로 생성해서 넣는다면 불가능하다
    arrayList.add(100)
    arrayList.add(200)
}

//6. 반복문-------------------------------------
fun repeat(){
    println("-----------------------------repeat--------------")

    val students = arrayListOf("j1","j2","j3","j4")

    for(name in students){
        println(name)
    }

    var sum = 0
    for (i in 1..10 step 2) {
        sum = sum + i;
    }
    sum = 0
    for (i in 10 downTo  1) {
        sum = sum + i;
    }
    sum = 0
    for (i in 1 until 100) {
        sum = sum + i;
    }
    
    //어레이에 인덱스 추가
    for ((index, name) in students.withIndex()){
        println("${index} 번째 ${name}")
    }

    //while은 똑같은듯 하다
}

//7. NonNull & Nullable ------------------------------------------
fun nullCheck(){
    //npe ::  null pointer exception :: java는 오직 runtime에서만 잡힌다...

    var name : String = "joyce"   //이상태는 NonNull타입이다. 그러므로...
    //var name2 : String = null     //이상태는 NonNull타입이다. 그러므로...
    var nullName : String? = null   //?를 붙여주면... null입력이 가능하다.

    var nameInUpeerCase = name.toUpperCase()
    //var nullNameToUppercase = nullName.toUpperCase();  //이러면 에러가 난다.
    var nullNameToUppercase = nullName?.toUpperCase()     // ?연산자를 추가해서  null이면 null반환 아니면 실행한다.


    //엘비스(프레슬리) 연산자 --- ?:    Nullable변수에서 값이 Null일때 Default값을 주려고 할때 쓴다.
    val lastName : String? = null;
    val fullName = name + " " + (lastName?: "No LastName")
    println(fullName)
}


//쌍느낌표 연산자  !! ::  Nullable이지만 요건 Null이 아닐꺼다 라고 컴파일러한테 인증해주는 경우에 사용한다.
//?와 let의조합을 통한 lambda식처리
fun ignoreNull(str : String?){
    //Nullable인자를 NotNull타입에 대입시키면 주석처럼 에러가 난다.

//        :: ERROR CDOE
//        val imNotNull : String = str
//        val upper = str.toUpperCase()

    val imNotNull : String = str!!    //!!느낌표 두개로 보증해준다면..   ---> 정말 내가 확실하다고 생각하지 않는다면 쓰지마시오...
    val upper = str.toUpperCase()     //사용가능하다.

//    val email : String? = "hyperion@naver.com"
    val email : String? = null
    email?.let{                 //email이 Null이 아니라면 let명령어를 통해 내용을 람다식 안으로 넣어준다
        println("my email is ${email}")
    }
}



