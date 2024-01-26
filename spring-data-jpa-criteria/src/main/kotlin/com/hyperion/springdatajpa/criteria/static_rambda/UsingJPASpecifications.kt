package com.hyperion.springdatajpa.criteria.static_rambda

import com.hyperion.springdatajpa.BookEntity
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor


/**
 * We can provide methods to create Specification instances:
 * ```java
 *interface Specification<T> {
 *     Predicate toPredicate(Root<T> root, CriteriaQuery query, CriteriaBuilder cb);
 * }
 * ```
 */
interface SpecificExecutorBookRepository : JpaRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity>
//end of interface


fun hasAuthor(author: String): Specification<BookEntity> {
    return Specification { book: Root<BookEntity>, cq: CriteriaQuery<*>, cb: CriteriaBuilder ->
        cb.equal(book.get<Any>("author"),author)
    }
}

fun titleContains(title: String): Specification<BookEntity> {
    return Specification { book: Root<BookEntity>, cq: CriteriaQuery<*>, cb: CriteriaBuilder ->
        cb.like(book.get("title"),"%$title%")
    }
}