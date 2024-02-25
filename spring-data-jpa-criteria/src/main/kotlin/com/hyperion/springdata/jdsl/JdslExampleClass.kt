package com.hyperion.springdata.jdsl

import com.hyperion.springdata.BookEntity
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository



interface BookJdslRepository : JpaRepository<BookEntity, Long>, KotlinJdslJpqlExecutor





