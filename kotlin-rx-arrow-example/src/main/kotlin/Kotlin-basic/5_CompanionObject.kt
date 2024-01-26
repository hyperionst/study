package `Kotlin-basic`/*
* 2024.010.10 joyce강의 2강
* https://www.youtube.com/watch?v=Q5noYbbc9uc&t=515s
*
*
* Companion Object : JAVA에서 Static 처럼 정적 메소드나 정적 변수를 선언할때 사용한다.
*
* */


class Book private constructor(val id: Int, val name: String){    //다른 곳에서는 생성하지 못하게 private 생성자..

    //선언 : (이름은 써도되고 안써도 되고 // 상속도 가능)
    companion object BookFactory : IdProvider {  //내 구문안에 있는것은 Static선언되어있는 것과 같은 효과가 난다.
        val myBook = "new book"
        fun create() = Book(0, myBook)
        override fun getId(): Int {
            return 40
        }


    }

}

interface IdProvider {
    fun getId() : Int   //Int를 반환하는 메소드
}



fun main() {
      //ErrorCode
//    val book = Book();
    val book = Book.create()  //companion object 이름을쓰거나 아님 아애 안쓰거나 해야한다.
    println("${book.id} ${book.name}")

    //java의 static way를 통해 접근한다.
    println("${Book.getId()}")
}