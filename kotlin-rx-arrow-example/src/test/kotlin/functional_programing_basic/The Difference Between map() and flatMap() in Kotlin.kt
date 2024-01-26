package functional_programing_basic

import com.hyperion.core.logger
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


/**
 * ## map() & flatMap()
 *
 * [https://www.baeldung.com/kotlin/map-vs-flatmap](https://www.baeldung.com/kotlin/map-vs-flatmap)
 *
 * ### map() is an extension function in Kotlin that is defined as:
 * > fun <T, R> Iterable<T>.map(transform: (T) -> R): List<R>
 *  - iterates over all elements of an Iterable<T> one by one
 *  - __During this iteration, it transforms every single element of type T to another element of type R__
 *  - it converts all elements of the receiving collection, and we’ll end up with a List<R>.
 *  - __This function is usually useful in one-to-one mapping situations.__
 *
 *
 * ###  flatMap() is usually useful for flattening one-to-many relationships.
 * > fun <T, R> Iterable<T>.flatMap(transform: (T) -> Iterable<R>): List<R>
 * - it transforms each element of type T into a collection of type R.
 * - instead of ending up with a List<Iterable<R>>, flatMap() flattens each Iterable<R> to its individual elements.
 * - Therefore, we’ll have a List<R> as a result.
 *
 */
class `The Difference Between map() and flatMap() in Kotlin` {

    class Order(val lines: List<OrderLine>)
    class OrderLine(val name: String, val price: Int)


    @Test
    fun exampleMap() {
        val order = Order(
            listOf(OrderLine("Tomato", 2), OrderLine("Garlic", 3), OrderLine("Chives", 2))
        )

        val name = order.lines.map { lineItem ->
            logger.info { lineItem.price }
            return@map lineItem.name
        }
        logger.info { "$name ${name.javaClass}" }
    }


    /**
     * ## FlatMap
     *
     *
     *
     */
    @Test
    fun exampleFlatMap() {
        //중첩되어있는 orderList의 List를 받았다고 한다면..
        val orders = listOf(
            Order(listOf(OrderLine("Garlic", 1), OrderLine("Chives", 2))),
            Order(listOf(OrderLine("Tomato", 1), OrderLine("Garlic", 2))),
            Order(listOf(OrderLine("Potato", 1), OrderLine("Chives", 2))),
        )
        logger.info { orders.map { orderItem -> orderItem.lines } } // List<List<OrderLine>> }

        //the flatMap() function flattens the one-to-many relationship between each Order and its OrderLines.
        val lines: List<OrderLine> = orders.flatMap { orderItem: Order -> orderItem.lines }
        //맵으로 이름만 뽑아낸후에.중복값을 삭제
        val names = lines.map { orderLine -> orderLine.name }.distinct()
        logger.info { names }

    }
}