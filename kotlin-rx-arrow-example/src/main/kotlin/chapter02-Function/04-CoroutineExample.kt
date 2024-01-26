package `chapter02-Function`

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.*


suspend fun longRunningTsk():Long {//(1)
    val time = measureTimeMillis {//(2)
        println("Please wait")
        delay(2000)//(3)
        println("Delay Over")
    }
    return time
}


/**
 *기다리는 메인함수
 */
//fun main(args: Array<String>) {
//    runBlocking {//(4)
//        val exeTime = longRunningTsk()//(5)
//        println("Execution Time is $exeTime")
//    }
//    println("mainmainmian")
//}

fun main() = runBlocking{
    val time = launch  { longRunningTsk() }
    println("Print after async ")
    println("main running")
}
