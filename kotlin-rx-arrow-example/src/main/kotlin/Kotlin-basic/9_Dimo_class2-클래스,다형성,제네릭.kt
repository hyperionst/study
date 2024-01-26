package `Kotlin-basic`

//-----------------------------------------------------------------------------------------------------------
//object 클래스. :: 싱글턴을 바로 생성해준다.
object counter {
    var count : Int = 0

    fun countUp(){
        count++
    }
    fun countDown(){
        count--
    }
}

fun objectClassTest() {
    //인스턴스 생성 없이 바로 사용가능하다. 단 Object Class명과 같이 사용해야한다. (Static way로...)
    counter.countUp()
    counter.countUp()
    counter.countDown()
    counter.countDown()

}
//클래스안에 Object Class를 선언하여 인스턴스간에 공통으로 사용할 기능을 묶을수 있다.
//이게 Companion Class이다. Java에서 Class안에 Static맴버를 선언하는 것과 같다.
class FoodPoll (val name: String){
    companion object {
        var total = 0    //난 내 인스턴스들의 전체 투표수를 담당할꺼다
    }
    var count = 0       //난 생성된 개별 인스턴스 투표수를 담당할꺼다.
    fun vote(){
        total++
        count++
    }
}

//-----------------------------------------------------------------------------------------------------------
//익명객체와 옵저버패턴
//코틀린에서 observer 패턴을 만들기
//이벤트가 발생하는 것을 잡아서 처리하도록 만들어놓은 패턴을 옵저버 패턴이라고 한다.

interface EventListener{    //이벤트 발생시키는 녀석과 이벤트를 처리하는녀석 사이를 이어줄 인터페이스를 구성
    fun onEvent(count : Int)
}

class Counter(var listener : EventListener){    //이벤트 Listener를 인스턴스 생성시 참조하도록 해야하고...

    fun count(){
        for(i in 0..100){
            if(i % 5 ==0) listener.onEvent(i)
        }
    }
}
class EventPrinter : EventListener {        //이벤트를 수신할사람은 인터페이스를 implement해서
    override fun onEvent(count: Int) {
        println("${count}-")
    }
    fun start() {
        val counter = Counter(this)   //EventPrinter전체 객체를 this로 넘기지만.. 실제로는 Counter에서 EventListener 구현부만 받아간다. (다형성 upcasting일려나..)
        counter.count()
    }
}

//익명 갹채로 전달도 가능하다.
//익명 객체로 인터페이스를 전달할때는 object키워드를 사용해야 생성할 수 있다.
class EvnetPrinterAnonymuse {
    fun Start() {
        val count = Counter(object : EventListener {
            override fun onEvent(count: Int) {
                println("${count}-")
            }
        })
    }
}


//-----------------------------------------------------------------------------------------------------------
//다형성
//Upcasting시 그냥 상위 자료형의 변수에 담으면 되지만....
//DownCasting시에는 as / is를 사용한다.
//as : 호환되는 자료형으로 변환해주는 casting 연산자
//ex>
// var a : Drink = Cola()
// var b = a as Cola          //변환 및 반환 둘다 가능~

//is : 조건절에서 사용하는 연산자로  // 변수가 자료형에 해당하는지 체크하고 변환 및 반환을 해준다.
// if(a is Cola){...}


fun testMetamorphosis() {
    var a = Drink()
    a.drink()

    var b : Drink = Cola()
    b.drink()
    //errorcode
    //b.washDish()   ---> 아직은 Drink타입이므로 접근 불가능
    if(b is Cola){    // is로 접근하면 중괄호 안에서는 임시로 캐스팅된다.
        b.washDish()
    }
    //b.washDish()   ---> 아직은 Drink타입이므로 접근 불가능

    var c = b as Cola
    c.washDish()       //다운캐스팅 완료
    b.washDish()       //객체 자체가 다운캐스팅 되므로 이전에 안되는 b역시 됨에 유의. 참조 변수는 참조 변수일뿐...


}
open class Drink{
    var name = "beverage"
    open fun drink(){
        println("${name} drink!!")
    }
}

class Cola : Drink(){
    var type = "cola"
    override fun drink() {
        println("drink ${type} in ${name}")
    }
    fun washDish() {
        println("wash ${type}")
    }
}

//-----------------------------------------------------------------------------------------------------------
//19. Generic
//컴파일러에 의해서 Superclass에 의한 캐스팅을 자동으로 맡겨놓으면 연산이 늘어나는 단점이 존재 :: 제네릭을 이용해서 단순화 시켜주는게 필요하다.
//관례적으로  T 를쓰며 늘어나는경우 T,U,V를 사용하는 것이 관례이다.
fun genericTest(){
    UsingGereric(AClass()).doShouting()
    UsingGereric(BClass()).doShouting()
    UsingGereric(CClass()).doShouting()

    doShouting(BClass())  //B class의 인스턴스를 전달한다.
}
open class AClass(){
    open fun shout(){
        println("A~~~")
    }
}
class BClass : AClass(){
    override fun shout() {
        println("B~~~")
    }
}
class CClass : AClass(){
    override fun shout() {
        println("C~~~")
    }
}

class UsingGereric<T : AClass> (val t : T){
    fun doShouting(){
        t.shout()
    }
}

//Generic 함수
fun <T: AClass> doShouting(t:T){
    t.shout()
}



//-----------------------------------------------------------------------------------------------------------
//
