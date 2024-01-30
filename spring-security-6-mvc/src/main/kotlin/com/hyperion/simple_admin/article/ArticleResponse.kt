package com.hyperion.simple_admin.article

import java.util.UUID

data class ArticleResponse(
    val id: UUID,
    val title: String,
    val content: String,
)