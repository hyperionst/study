package com.hyperion.simple_admin.article

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * Spring Security Auth TEST용 Simple Controller
 */
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/api/article")
class ArticleController(private val articleService: ArticleService) {


    @GetMapping("/all")
    fun listAll(): List<ArticleResponse> =
        articleService.findAll()
            .map { it.toResponse() }

    @GetMapping("/one")
    fun listOne(): ArticleResponse =
        articleService.findAll().map { it.toResponse() }.first()

    private fun Article.toResponse(): ArticleResponse =
        ArticleResponse(
            id = this.id,
            title = this.title,
            content = this.content,
        )
}