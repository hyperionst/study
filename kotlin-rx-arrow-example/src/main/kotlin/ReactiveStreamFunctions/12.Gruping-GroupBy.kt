package `chapter05-06`.ReactiveStreamFunctions

import io.reactivex.Observable


//grouipBy -> 스트림에 대한 데이터 처리후 결과값이 같은 녀석들로 그룹핑 해서 처리한다.
//return은 GroupedObservable<k,v>이며 스트림을 전부 처리하기 전까지는
//데이터가 완성되지 않는다.
// subscribe할때 최종 결과가 나오면 한번에 처리하는 blockingSubscribe로 구독해야 원하는 그룹핑 결과를 볼수있다.
fun main(args: Array<String>) {
    val observable = Observable.range(1, 30)

    observable.groupBy {
        it % 5
    }.blockingSubscribe { gob ->   //나 역시 Observable이므로... subscribe로 받아야한다.
        run{
            println("Key ${gob.key} ")
            gob.subscribe {item ->
                println("Received $item")
            }
        }
    }
}