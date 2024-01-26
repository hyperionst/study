package com.hyperion.springdatajpa

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
data class BookEntity(
    @Id
    val id : Long,
    val title : String,
    val author : String = "",
){
    constructor():this( 0,"","")
}
