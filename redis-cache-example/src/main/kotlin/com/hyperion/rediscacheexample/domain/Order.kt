package com.hyperion.rediscacheexample.domain

import jakarta.persistence.*


@Entity
@Table(name = "ORDERS")
data class Order(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var  id : Int = 0,
    var  orderCode : String = "",
    var  orderObject : String = "",
    var  orderStatus : String = "",
    var  orderPrice : Int = 0,
)