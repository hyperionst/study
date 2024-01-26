package com.hyperion.webfluxkotlin.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


/**
 * ## Entity 구성시 조심해야 할것들
 *
 * ### 생성자 생성
 *
 * - 생성자 내부에서 변수 선언된 값이 MongoDB Collection안에 데이터가 없을 경우 Mapping이 되지 않는다.
 * - org.springframework.data.mapping.model.MappingInstantiationException:
 * - 해당 변수값을 초기화하면 정상 조회 로직 진행
 *
 *
 * ### 클래스 내부 변수
 *
 * - Id값은 val 선언을 하지 않는다. (id값은 MongoDB 내의 로직에 의해 수행되기 때문)
 * - private 선언시에는 Mapping이 되지 않는다.
 * - Kotlin의 val 선언은 java에서 final로 정의하는 것과 같기 때문에 Mapping이 되지 않는다.
 *
 *
 */
@Document(value = "testCollection")
data class BaseDocument(
    @Id
    val id : Any?,
    val baseId : String,
    val baseName : String,
    val baseNumber : String = "sd",
    val baseAge: String,
)
