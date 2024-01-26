package com.hyperion.springdatajpa.criteria.basicway

import com.hyperion.springdatajpa.BookEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.TypedQuery
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Root
import org.springframework.stereotype.Repository

/**
 * Spring Data Jpa CriteriaAPI구현 1
 * Class타입 심플심플~
 */
@Repository
class BookDao(val em: EntityManager) {

//    fun findBooksByAuthorNameAndTitle(authorName: String, title: String): MutableList<BookEntity> {
//        val cb: CriteriaBuilder = em.getCriteriaBuilder();
//        val cq: CriteriaQuery<BookEntity> = cb.createQuery(BookEntity::class.java)
//
//        val book: Root<BookEntity> = cq.from(BookEntity::class.java)
//        val pathAuthor : Path<String> = book.get("author")
//        val authorNamePredicate = cb.equal(pathAuthor, authorName)
//        val pathTitle : Path<String> = book.get("title")
//        val titlePredicate = cb.equal(pathTitle, "%$title%")
//        cq.where(authorNamePredicate, titlePredicate)
//
//        val query: TypedQuery<BookEntity> = em.createQuery(cq)
//        return query.getResultList()
//    }

    fun findBooksByAuthorNameAndTitle(authorName: String?, title: String): List<BookEntity> {
        val cb = em.criteriaBuilder
        val cq = cb.createQuery(BookEntity::class.java)

        val book = cq.from(BookEntity::class.java)
        val authorNamePredicate = cb.equal(book.get<Any>("author"), authorName)
        val titlePredicate = cb.like(book.get("title"), "%$title%")
        cq.where(authorNamePredicate, titlePredicate)

        val query = em.createQuery(cq)
        return query.resultList
    }


    fun findBooksAll() : MutableList<BookEntity> {
        val cb: CriteriaBuilder = em.getCriteriaBuilder();
        val cq: CriteriaQuery<BookEntity> = cb.createQuery(BookEntity::class.java)

        val book: Root<BookEntity> = cq.from(BookEntity::class.java)
        val query: TypedQuery<BookEntity> = em.createQuery(cq)
        return query.getResultList()
    }




}