package chapter09.resource

import io.reactivex.Observable
import java.io.Closeable

fun main(args: Array<String>) {
    Observable.using({
        Resource()
    },{
            resource:Resource->
        Observable.just(resource)
    },{
            resource:Resource->
        resource.close()
    }).subscribe {
        println("Resource Data ${it.data}")
    }
}

class Resource(): Closeable {

    init {
        println("Resource Created")
    }

    val data:String = "Hello World"

    override fun close() {
        println("Resource Closed")
    }
}