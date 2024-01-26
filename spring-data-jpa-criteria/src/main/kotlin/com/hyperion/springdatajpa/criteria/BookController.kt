package com.hyperion.springdatajpa.criteria

import com.hyperion.core.logger
import com.hyperion.springdatajpa.BookEntity
import com.hyperion.springdatajpa.criteria.basicway.BookDao
import com.hyperion.springdatajpa.criteria.interfaceway.BookRepository
import com.hyperion.springdatajpa.criteria.static_rambda.SpecificExecutorBookRepository
import com.hyperion.springdatajpa.criteria.static_rambda.hasAuthor
import com.hyperion.springdatajpa.criteria.static_rambda.titleContains
import org.springframework.data.jpa.domain.Specification
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class BookController(private val bookDao: BookDao, private val bookRepository: BookRepository, private val spec : SpecificExecutorBookRepository) {

    @GetMapping("/book")
    fun getAll() : MutableList<BookEntity> {
        return bookDao.findBooksAll()
    }

    @GetMapping("/book/{an}/{title}")
    fun getBook(@PathVariable an:String, @PathVariable title:String) : List<BookEntity> {
        logger.info {"class $an, $title"}

        return bookDao.findBooksByAuthorNameAndTitle(an,title)
    }



    @GetMapping("/ibook/{an}/{title}")
    fun getBookByInt(@PathVariable an:String, @PathVariable title:String) : List<BookEntity> {
        logger.info {"Interface $an, $title"}

        return bookRepository.findBooksByAuthorNameAndTitle(an,title)
    }



    @GetMapping("/sbook/{an}/{title}")
    fun getBookBySpecificExecutor(@PathVariable an:String, @PathVariable title:String) : List<BookEntity> {
        logger.info {"SE $an, $title"}

        //조건을 하나씩으로 구성한 함수들의 연속된 사용으로 처리할수 있다.
        return spec.findAll(Specification.where(hasAuthor(an)).and(titleContains(title)))
    }



}