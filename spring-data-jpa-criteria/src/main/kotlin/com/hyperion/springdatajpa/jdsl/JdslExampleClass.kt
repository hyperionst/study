package com.hyperion.springdatajpa.jdsl

import com.hyperion.springdatajpa.BookEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


interface BookJdslRepository : JpaRepository<BookEntity, Long>, KotlinJdslJpqlExecutor





