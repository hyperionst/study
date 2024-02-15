package com.hyperion.rediscacheexample.controller

import com.hyperion.rediscacheexample.domain.Order
import com.hyperion.rediscacheexample.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/order")
class OrderController(val orderService: OrderService) {


    @PostMapping
    fun createOrder(@RequestBody order: Order): Order? {
        return orderService.createOrder(order)
    }

    @GetMapping
    fun getOrder() : ResponseEntity<List<Order?>> {
        return ResponseEntity.ok(orderService.getAllOrders())
    }

    @GetMapping("/{id}")
    fun getOrder(@PathVariable id: Int?): Order? {
        return orderService.getOrder(id!!)
    }

    @PutMapping("/{id}")
    fun updateOrder(@PathVariable id: Int, @RequestBody order: Order): Order? {
        return orderService.updateOrder(order, id)
    }

    @DeleteMapping("/{id}")
    fun deleteOrder(@PathVariable id: Int): String {
        orderService.deleteOrder(id)
        return "Order with id: $id deleted."
    }
}