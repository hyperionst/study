package chapter03.`Observable-Observer-Subject`

import io.reactivex.rxkotlin.toObservable

fun main() {
    val connectableObservable = listOf("String 1","String 2","String 3","String 4","String 5").toObservable()
        .publish()//1    .publish를 통해서 Cold를 Hot으로 변경한다.
    connectableObservable.subscribe({ println("Subscription 1: $it") })//2
    connectableObservable.map(String::reversed)//3   --> map을 통해서 스트링 변환후 전달
        .subscribe({ println("Subscription 2 $it")})//4

    //Connect콜이 되기전까지는 데이터전송을 하지 않고 있다가...
    println("Connect Call")
    connectableObservable.connect()//5     지금부터 방출하기 시작한다.  (연결되어있는 두녀석 모두한테..)
    println("end Connect Call")

    //이미 위에서 다 방출해 버렸기 때문에...
    connectableObservable.
    subscribe({ println("Subscription 3: $it") })//6 //Will never get called
}