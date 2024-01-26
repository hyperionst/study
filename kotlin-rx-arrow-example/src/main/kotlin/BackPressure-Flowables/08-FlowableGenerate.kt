package chapter04.`BackPressure-Flowables`

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

//create 대신 generate를 사용했다.  (Create는 한방에 생성해요~)
//generate는 컨수머가 데이터를 요청하슨 시점에 데이터를 생성하는 구조이다.
fun main(args: Array<String>) {
    val flowable = Flowable.generate<Int> {
        it.onNext(GenerateFlowableItem.item)
    }//(1)

    flowable
        .map { MyItemFlowable(it) }
        .observeOn(Schedulers.io())
        .subscribe {
            runBlocking { delay(100) }
            println("Next $it")
        }//(2)

    runBlocking { delay(700000) }
}

data class MyItemFlowable(val id:Int) {
    init {
        println("MyItemFlowable Created $id")
    }
}


//int value의  MaxValue까지 생성한후 MinValue로 돌아가서 계속 증가하는 역활을 하게된다.
object GenerateFlowableItem {
    var item:Int = 0//(3)
    get() {
        field+=1
        return field//(4)
    }
}