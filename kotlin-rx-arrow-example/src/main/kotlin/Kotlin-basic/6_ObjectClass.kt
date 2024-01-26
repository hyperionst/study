package `Kotlin-basic`
/*
* 2024.010.10 joyce강의 2강
* https://www.youtube.com/watch?v=Q5noYbbc9uc&t=515s
*
*
* Object ::
* - class를 선언할때 object키워드를 통해 생성한다.
* - 생성된 객체는 싱글턴으로 구현된다.
* */


//object는 싱글턴이다!
object CarFactory {
    var cars = mutableListOf<Car>()

    fun makeCar(hp: Int) : Car {
        val car = Car(hp)
        cars.add(car)
        return car
    }
}

//사용할 데이터 클래스 선언
data class Car(val hp : Int, val name : String = "NoNameCar")


fun main() {
    //object 클래스는 선언과 함께 바로 사용이 가능하다 // 심지어 Thread Safe까지 넣어준듯 하다.
    val car1 = CarFactory.makeCar(10)
    val car2 = CarFactory.makeCar(20)

    println(CarFactory.cars.size)
}