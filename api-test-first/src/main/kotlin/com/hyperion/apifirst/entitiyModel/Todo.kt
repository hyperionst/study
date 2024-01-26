package com.hyperion.apifirst.entitiyModel

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank


@Entity  //entity로된 모든클래스에 대한 데이터베이스테이블이 생성된다.
data class Todo (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0,

    @NotBlank //null or blank가 아니어야한다.
    var todoDescription : String,

    @NotBlank
    var todoTargetDate : String,

    @NotBlank
    var status : String
){
    constructor():this( 0,"","","")
}