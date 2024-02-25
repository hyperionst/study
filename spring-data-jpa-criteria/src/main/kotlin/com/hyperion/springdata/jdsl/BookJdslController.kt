package com.hyperion.springdata.jdsl

import com.hyperion.core.logger
import com.hyperion.springdata.BookEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/jdsl")
@RestController
class BookJdslController(private val bookService: BookService, private val bookJdslRepository: BookJdslRepository) {

    @GetMapping("/authors")
    fun getAuthors() : List<String?> {
        return bookService.getAuthors();
    }

    @GetMapping("/books")
    fun getAll() : List<BookEntity?> {
        return bookService.getBooks();
    }

    @GetMapping("/book/{id}")
    fun getBook(@PathVariable id:Long, ) : List<BookEntity?> {
        logger.info {"class $id"}
        return  bookService.getBook(id)
    }

    @PostMapping("/update.book")
    fun updateBook(@RequestBody  book:BookEntity ) : Int {
        logger.info {"update $book"}
        return  bookService.updateBook(book)
    }

    @PostMapping("/save.book")
    fun saveBook(@RequestBody  book:BookEntity ) : BookEntity {
        logger.info {"update $book"}
        return bookJdslRepository.save(book)
    }

    @DeleteMapping("/delete.book/{id}")
    fun deleteBook(@PathVariable id :Long) : Int {
        logger.info {"delete $id"}
        return bookService.deleteBook(id)
    }



}