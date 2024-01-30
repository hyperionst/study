package com.hyperion.springsecuritymvc.article

import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleRepository: ArticleRepository
) {
    fun findAll(): List<Article> =
        articleRepository.findAll()
}