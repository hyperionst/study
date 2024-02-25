package `Kotlin-basic`

import kotlinx.coroutines.*


/**
 * ## Scope
 * #### global scope
 *  - 프로그램의 어디서나 제어, 동작이 가능한 기본범위
 *  -
 * #### coroutine scope
 * - 특정한 목적의 dispatcher 를 지정하여 제어 및 동작이 가능한 범위
 *
 * #### dispatcher List
 * - Dispatchers.Default    :: 기본적인 백그라운드 동작
 * - Dispatchers.IO         :: I/O에 최적화된 동작
 * - Dispatchers.Main       :: 메인스레드에서 함께 동작
 *
 * #### scope의 실행
 * - launch ::  결과값없이 진행할때.  리턴은 job
 * - async  :: 결과값을 기다린다.     리턴은 Deffered<>
 *
 * #### delay(), join(), await()
 * - delay(milsecond) 잠시 대기시킨다
 * - job.join() : job의 실행이 끝날때까지 대기
 * - Deferred.await() : 결과값이 나올때까지 기다리는 함수
 *
 * #### 코루틴의 중단 :: cancel()
 * - case1 : 코루틴 내부의 delay() 또는 yield()함수가 사용된 위치까지 수행된뒤 종료
 * - case2 : cancel()로 인해 isActive 가 false 가 되면 이를 확인하여 수동으로 종료함
 *
 * #### withTimeoutOrNull()
 * - 지정된 시간안에 데이터가 나오면 진행 아니면 null을 반환
 */
fun main(){
    val scope = CoroutineScope(Dispatchers.Default)
    val coroutineA = scope.launch {  }    //반환값이 없는 사용
    val coroutineB = scope.async {  }    //난 반환이 되어야만 한다.



    //내부 내용이 실행되는 동안 main함수를 블록킹 하고 있도록 해놓고 하자 안그럼 끝나버린다.
    runBlocking {
        launch {
            for(i in 1..5) println(i)
        }
        var b = coroutineB.await()
    }

    runBlocking {
        val anydata : Any? = withTimeoutOrNull(50){
            for (i in 1..10){
                println(i)
                delay(10)
            }
            return@withTimeoutOrNull "Finish"
        }
        println(anydata)
    }


}
