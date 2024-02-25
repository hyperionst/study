package `kotlin-basic-remind`

import `Kotlin-basic`.person
import com.hyperion.core.logger
import org.junit.jupiter.api.AfterEach
import kotlin.test.Test


class `Kotlin Basic Remind` {


    /**
     * ### 변수
     *  kotlin은 Reference Data Type을 사용한다. : 객체를 생성하고 데이터가 할당된다.
     * 원시타입은 지원하지 않는다. (컴파일시 필요한 레퍼런스타입중에서 기본형으로 대체된다.)
     * 최상위 클래스는 Any이다.
     *
     * 숫자형데이터의 경우 자리수 구분을위해 _를 사용할수 있다. 값에는 영향을 주지 않는다.
     * 작은 숫자는 기본적으로 int로 추론하는데... 더 작을려면 Short, byte를 사용하고 명시적으로 지정해야한다.
     * char(16bits)-참고로 선언할때는 무조건 문자를 쓰시오(타입추론땜에) / byte(8), short(16), int(32), long(64)
     */
    @Test
    fun `Value & Variable` () {
        //아무거나 다들어간다.
        var anyVriable : Any = 1
        anyVriable = false
        anyVriable = "string"
        anyVriable = listOf(1,2,3,4,5)
        println(anyVriable)
    }

    //--------------------------------------------------------//
    /**
     * ### 비교연산자
     * >  a == b, a != b : a-b의 값이 같은지?
     * >  a === b, a !== b : 참조 주소를 비교한다.
     */
    @Test
    fun `비교 연산자` () {
    }

    //--------------------------------------------------------//
    /**
     * when, if
     */
    @Test
    fun `제어문`() {
        fun getBit() : Bit{
            return Bit.zero
        }

        //Enum사용시
        val number = when(getBit()){
            Bit.zero -> 0
            Bit.one -> 1
        }

        //범위제어
        val score = 90
        when(score){
            in 90..100 -> println("A")
            in 10..80 -> print("soso")
            else -> println("okay")
        }

    }
    enum class Bit {
        zero, one
    }

    //--------------------------------------------------------//
    /**
     * ### Function
     * - default value를 이용해서 오버로딩을 처리한다
     * - 호출시 변수명을 지정해서 데이터를 넣을수 있다.
     */
    @Test
    fun `function test`(){
        fun namedArgu(a: String, b:String, c:String = "c"){
            println()
        }
        namedArgu(a = "", b="1", c="2")
        namedArgu(a = "", b="1")
    }

    //--------------------------------------------------------//

    /**
     * ### Class 기본적인 모습
     * - 선언시 생성해둔것이 주생성자, 추가적인 생성자는 constructor 키워드를 통해서 생성한다
     * ### 상속
     * - 부모클래스는 open키워드를 사용해서 상속을 허용해준다.
     * - 메서드 또한 open키워드를 통해 상속한다.
     */

    class Person(
        private val name: String,
        private val age: Int){

        //부생성자들 선언
        constructor() : this("dn", 1)
        constructor(name: String) : this(name, 15)
        constructor(name:String, age: Int, etc:String) : this(name,age){
            println(etc)
        }

        fun sayHello() {
            println("Hello")
        }
    }
    @Test fun `class test`() {
        val pe1 =  Person()
        val pe2 =  Person("test")
        val pe3 =  Person("test", 12, "etc")
    }


    /**
     *  -  널러블 타입 체크 하기 .
     *      ?.let
     *      is
     *      when
     */
    @Test fun `null check`() {
        val pe1 =  Person()
        val pe2 =  Person("test")
        val pe3 =  Person("test", 12, "etc")
    }

    class car(
        val name: String,
        val jvmField: JvmField
    ){

    }


    data class Student(val name: String, val subjects: List<Subject>)
    data class Subject(val name: String, val grades: List<Int>)
    data class Grade(val studentName: String, val subjectName: String, val score: Int)
    @Test fun `flat-map`() {

        val students = listOf(
            Student("Alice", listOf(Subject("Math", listOf(90, 85, 92)), Subject("English", listOf(88, 87)))),
            Student("Bob", listOf(Subject("Math", listOf(78, 80)), Subject("History", listOf(92, 88)))),
            Student("Charlie", listOf(Subject("English", listOf(95, 91)), Subject("History", listOf(84, 89))))
        )

        // 각 학생의 모든 성적을 평탄화하여 리스트로 만들기
        val allGrades = students.flatMap { student ->
            student.subjects.flatMap { subject ->
                println("${subject}, ${student.name}" )
                subject.grades.map { Grade(student.name, subject.name, it) }
            }
        }
        println("----------------------------------")
        println(allGrades)
    }





}