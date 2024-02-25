package `Kotlin-basic`/*
* 27강부터
*
*
* */


//-----------------------------------------------------------------------------------------------------------
//콜랙션 함수
//컬랙션, 배열에 일반함수, 람다를 이용해서 처리한다.
//forEach, : 개별로 수행
//filter,  : 조건을 구성하고 그 조건에 맞는것을 다시 리스트로
//map ,    : 연산을 수행한후 다시 리스트로
//any,     : 하나라도 맞는게 있다면 트루
//all,     : 전부 맞다면 트루
//none,    : 아에 맞는게 없다면 트루
//firstOrNull,  : 조건에 해당하는 첫번째 를 리턴하고 // 없다면 null을 반환한다.
//lastOrNull,   : 조건에 맞는 마지막을 리턴하고 // 없다면  null
//contains : 조건을 포함하고있는 수를 계산해준다.
//count    : 그냥 카운트 할수도 있고

fun funccc(){
    val nameList = listOf("박수영","김지수","김다현", "신유나", "김지우")
    nameList.forEach{print(it + " ")}
    println()
    println(nameList.filter { it.startsWith("김") })
    println(nameList.map { "이름" + it })
    println(nameList.any { it == "김지연" })
    println(nameList.all { it.length == 3 })
    println(nameList.none { it.startsWith("이")})
    println()
    
}

//-----------------------------------------------------------------------------------------------------------
//https://www.youtube.com/watch?v=pFk72oghDLo
//컬랙션 함수 두번째
//associateBy : 아이템에서 key를 추출하여 map으로 변환 --> 여러 속성을가진 객체로 구성된 List에서 일부 속성을 키로지정하고 나머지속성을 value로 만든 map으로변환
//groubpBy    : 지정된 속성을 기준으로 그 값이 같은 값들을 LIST로 묶어서 map으로 만든다.
//partition   : 조건을 걸어 true / false의 각각의 List 로 구분해준다. PAIR라는 클래스로 리턴된다.  나눠서 받던지 var(a,b) = <pair...>, pair에서 각각접근하던지...
//flatmap     :지 중괄호 안에서 새로운 값들이 생성되면 이들을 모아서 하나의 컬랙션으로 반환해준다.
//zip         : 두 컬랙션의 내용을 각각 짝으로 어서 리스트로 바환한다    alpabets zip number ==> [(a,1),(b,2)....  ]

fun collectionFunc() {
    val numbers = listOf(-3,7,2,-10,1)

    println(numbers.flatMap { listOf(it * 10, it + 10) })
    println(numbers.flatMap { listOf(it*10, it +10 ) })
}


//-----------------------------------------------------------------------------------------------------------
//lateinit & lazy
//상수 : 절대 별경불가 const 를 val var 앞에 선언  // companion object안에서 선언되어야한다.

// 늦은 초기화 :: 자료형만 선언하고 객체는 나중에 할당하면 안될까 싶을때 사용한다.
// lateinit var 로 선언하면 위에서 하는 것이 가능해진다.
// 단 초기화 전까지는 변수를 사용하면 컴파일러에서 에러를 발생시킨다.  String을 제외한 기본자료형에는 사용불가능
// lateinit변수의 사용전 초기화 여부 체크는  ::a.initialized  를 통해 확인후 사용한다.

//지연대리자 속성 --> lazy delegatge properties
//코드상에서는 초기화가되있는것처럼 보이지만 실제로는 첨으로 실행되는 시점에 초기화가 되는 변수로....
//람다를 통해서 초기화가되므로... 초기화 시점에 특정한 동작을 지정할수 있는 장점이 있다더라


fun test1(){

    val num : Int by lazy {
        println("num을 초기화 합니다")
        7
    }
    println("시작이다.")
    println(num)          //--> 여기서 초기화 되는데 초기화시 선언된 람다내용이 다 실행된다.
    println(num)          //--> 이미 초기화 되있으니... 람다내용이 다시 실행되는 일은 없다
}
class LateInitSample {
    lateinit var text1 : String
    //errcode
    //var text2 : String     ==> 초기화값을 넣기 전까지 에러가 난다.
    fun getLateInitText(): String {
        if (::text1.isInitialized){
            return text1
        }else{
            return "기본값이 없어서 제공하는 기본값"
        }
    }
}

//-----------------------------------------------------------------------------------------------------------

fun main() {
    val name1: String by lazy { "123456" }      //value 전용임
    lateinit var text2 : String                 //variable 전용임
    
}





