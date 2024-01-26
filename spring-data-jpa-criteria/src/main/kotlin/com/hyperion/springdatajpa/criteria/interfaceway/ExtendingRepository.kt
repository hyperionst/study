package com.hyperion.springdatajpa.criteria.interfaceway

import com.hyperion.springdatajpa.BookEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository



/**
 * 조건데이터의 유무에 따라서 쿼리 구성할때 코드가 길어지는 부분을 제거하기 위해
 *
 */
interface BookRepositoryCustom {
    fun findBooksByAuthorNameAndTitle(authorName: String?, title: String?): List<BookEntity>
}

interface BookRepository : JpaRepository<BookEntity?, Long?>, BookRepositoryCustom


@Repository
class BookRepositoryImpl(private val em: EntityManager ) : BookRepositoryCustom {

    override fun findBooksByAuthorNameAndTitle(authorName: String?, title: String?): List<BookEntity> {
        val cb = em.criteriaBuilder
        val cq = cb.createQuery(BookEntity::class.java)

        val book = cq.from(BookEntity::class.java)
        val predicates: MutableList<Predicate> = mutableListOf<Predicate>()

        if (authorName != null) {
            predicates.add(cb.equal(book.get<Any>("author"), authorName))
        }
        if (title != null) {
            predicates.add(cb.like(book.get("title"), "%$title%"))
        }
        cq.where(*predicates.toTypedArray())   //List를 vararg에 넣으려면 어래이로 만든후 전개연산자인 *를 써줘야한다.

        return em.createQuery(cq).resultList
    }
}