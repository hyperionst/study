package functional_programing_basic

import arrow.core.*
import com.hyperion.core.logger
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

/**
 * ## Functional Error Handling in Kotlin
 * In this tutorial, we’ll learn about functional error handling patterns in Kotlin.
 *
 *[https://www.baeldung.com/kotlin/functional-error-handling](https://www.baeldung.com/kotlin/functional-error-handling)
 *
 *
 * If recoverable failures become function results,
 * then their compensation logic must enter the standard execution flow of our program.
 * The error handling programming model gets structured back again.
 * Then, only fatal exceptions will short-circuit execution flows.
 * This is acceptable because there’s nothing we can do to guard against them anyway.
 *
 *
 *
 */

data class Order(var id : Any, var customerId: Int)
data class Customer(var id : Int , var email : String ="default")


class ErrorHandlingPatterns {
    //Optjon Pattern
    private fun findOrder(id: Int): Option<Order> = Option.catch {
        if (id > 0) Order(id, 1) else throw Exception("Order Id must a positive number")
    }

    val orderId = when(val option = findOrder(1)) {
        is Some -> option.value.id
        else -> -1
    }


    @Test
    fun whenCallingFindOrderWithPositiveId_thenSuccess() {
        assertIs<Some<String>>(findOrder(1))
    }

    @Test
    fun whenCallingFindOrderWithNegativeId_thenFailure() {
        assertIs<None>(findOrder(-1))
    }


    //Either pattern
    fun findOrder2(id: Int): Either<Throwable, Order> = Either.catch {
        if (id > 0) Order(id, 1) else throw Exception("Order Id must be a positive number")
    }

    fun findCustomer(id: Int): Either<Throwable, Customer> = Either.catch {
        if (id == 1) Customer(id, "john.doe@mycompany.com") else throw Exception("Cannot find any customer for id $id")
    }

    @Test
    fun whenCallingFindOrderWithPositiveId2_thenSuccess() {

        val either = findOrder2(1)
            .map(Order::customerId)
            .flatMap { it: Int -> findCustomer(it) }
            .map(Customer::email)
        logger.info {either.leftOrNull()}
        logger.info {either.getOrElse { "NoData" }}

        assertEquals("john.doe@mycompany.com", either.getOrNull())
    }

    @Test
    fun whenCallingFindOrderWithNegativeId2_thenFailure() {
        val either = findOrder2(-1)
            .map(Order::customerId)
            .flatMap(this::findCustomer)
            .map(Customer::email)
        logger.info {either.leftOrNull()}
        assertTrue(either.isLeft())
    }




}