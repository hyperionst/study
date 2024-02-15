package com.hyperion.rediscacheexample.service

import com.hyperion.rediscacheexample.domain.Order

interface OrderService {
    fun createOrder(order: Order): Order?

    fun getOrder(orderId: Int): Order?

    fun updateOrder(order: Order, orderId: Int): Order?

    fun deleteOrder(orderId: Int)

    fun getAllOrders(): List<Order?>?
}