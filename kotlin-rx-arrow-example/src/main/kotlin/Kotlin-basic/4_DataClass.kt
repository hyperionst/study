package `Kotlin-basic`/*
* 2024.010.10 joyce강의 2강
* https://www.youtube.com/watch?v=Q5noYbbc9uc&t=515s
*
*
* Data Class : 동작이 없이 데이터만 들어가있는 클래스
* POJO class를 만들기 위해 kotlin 에서 지원하는 클래스이다.
* */


/**
 * 데이터클래스는 이렇게 선언만 하면 끝납니다.
 * 다음과 같이 코틀린에서 선언했다면, 아래의 내용들이 따라온다.
 * - 타입정보(선언시 각 맴버변수들을 이미 알려줬고..)
 * - 생성자 (맴버변수가 선언되면 자동으로 생성자는 있는거고...)
 * - ToString, hashcode 역시 자동으로 생성되고
 */
data class Ticket(val companyName: String, val name : String, var date : String, var seatNumber : Int)
//toString(), hashCode(), eaquals(), copy()가 자동으로 생성된다.
//

class TicketNormalClass(val companyName: String, val name : String, var date : String, var seatNumber : Int)

fun main(){
    val ticketA = Ticket("ka", "jc", "20202020", 14)
    val ticketB = TicketNormalClass("ka", "jc", "20202020", 14)

    println(ticketA)
    println(ticketB)
}


