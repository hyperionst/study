package `chapter02-Function`

fun isEven(n:Int):Boolean = ((n % 2) == 0)

inline fun highOrderFunc(a:Int, validCheckFunc:(a:Int)->Boolean){
    if(validCheckFunc(a)) {//(2)
        println("a $a is Valid")
    } else {
        println("a $a is Invalid")
    }
}


fun main() {
    highOrderFunc(12){ a:Int -> isEven(a)}//(3)
    highOrderFunc(19){ a:Int -> isEven(a)}
}