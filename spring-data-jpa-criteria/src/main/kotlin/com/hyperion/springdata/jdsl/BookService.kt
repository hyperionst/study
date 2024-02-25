package com.hyperion.springdata.jdsl

import com.hyperion.springdata.BookEntity
import org.springframework.stereotype.Service


@Service
class BookService(private val bookRepository: BookJdslRepository) {


    /**
     * Simple JDSL Test
     */
    fun getAuthors() : List<String?> {
        val actual = bookRepository.findAll {
            select(
                path(BookEntity::author),
            ).from(
                entity(BookEntity::class),
            ).orderBy(
                path(BookEntity::id).asc()
            )
        }
        return actual
    }


    /**
     *
     */
    fun getBooks() : List<BookEntity?> {
        val actual = bookRepository.findAll {
            select(
                entity(BookEntity::class),
            ).from(
                entity(BookEntity::class)
            )
        }
        return actual
    }

    fun getBook(id:Long) : List<BookEntity?> {
        val actual = bookRepository.findAll {
            select(
                entity(BookEntity::class),
            ).from(
                entity(BookEntity::class)
            ).where(
                path(BookEntity::id).equal(id),
            )
        }
        return actual
    }


    /**
     * updateBook ::
     */
    fun updateBook(book:BookEntity) : Int {
        val actual = bookRepository.update {
            update(
                entity(BookEntity::class),
            ).set(
                path(BookEntity::author), book.author
            ).set(
                path(BookEntity::title), book.title
            ).where(
                path(BookEntity::id).equal(book.id),
            )
        }
        return actual
    }


    /**
     * deleteBook ::
     */
    fun deleteBook(id: Long) : Int {
        val actual = bookRepository.delete() {
            deleteFrom(
                entity(BookEntity::class),
            ).where(
                path(BookEntity::id).equal(id)
            )
        }
        return actual
    }


}