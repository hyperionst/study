package com.hyperion.rediscacheexample.service

import com.hyperion.rediscacheexample.domain.Order
import com.hyperion.rediscacheexample.exceptions.OrderNotFoundException
import com.hyperion.rediscacheexample.exceptions.OrderStatusException
import com.hyperion.rediscacheexample.repository.OrderRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl (val orderRepository: OrderRepository): OrderService{

    override fun createOrder(order: Order): Order {
        return orderRepository.save(order)
    }

    @Cacheable(value = ["Order"], key = "#orderId", cacheManager = "redisCacheManager")
    override fun getOrder(orderId: Int): Order {
        return orderRepository.findById(orderId).orElseThrow { OrderNotFoundException("Order Not Found") }
    }

    @CachePut(value = ["Order"], key = "#orderId", cacheManager = "redisCacheManager")
    override fun updateOrder(order: Order, orderId: Int): Order {
        /*
        order status 변화주기
        status: ready -> processing -> shipped -> delivered
            */

        val orderObject: Order =
            orderRepository.findById(orderId).orElseThrow { OrderNotFoundException("Order Not Found") }
        if (orderObject.orderStatus == "ready") {
            orderObject.orderStatus = "processing"
        } else if (orderObject.orderStatus == "processing") {
            orderObject.orderStatus = "shipped"
        } else if (orderObject.orderStatus == "shipped") {
            orderObject.orderStatus = "delivered"
        } else {
            throw OrderStatusException("Order Status Cannot Change")
        }

        return orderRepository.save(orderObject)
    }

    @CacheEvict(value = ["Order"], key = "#orderId", cacheManager = "redisCacheManager")
    override fun deleteOrder(orderId: Int) {
        val orderObject: Order =
            orderRepository.findById(orderId).orElseThrow { OrderNotFoundException("Order Not Found") }
        orderRepository.delete(orderObject)
    }

    override fun getAllOrders(): List<Order> {
        return orderRepository.findAll()
    }
}