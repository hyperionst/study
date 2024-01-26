package `chapter02-Function`

fun main() {
    val sum = {x :Int, y: Int -> x+y}
    val mult : (Int)-> Int = {x -> x*x}

    println(sum(2,3))
    println(mult(2))
}