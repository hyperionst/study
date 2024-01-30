package com.hyperion.simple_admin.article

import java.util.UUID

data class Article(
    val id: UUID,
    val title: String,
    val content: String,
)