package com.hyperion.rediscacheexample.repository

import com.hyperion.rediscacheexample.domain.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface OrderRepository : JpaRepository<Order, Int>
