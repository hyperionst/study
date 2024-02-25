package com.hyperion.springdata

import jakarta.persistence.Entity
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
