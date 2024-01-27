package com.hyperion.r2bdcwebfluxexample


import org.springframework.data.annotation.Id


class Tutorial {
    @Id
    var id = 0

    var title: String? = null
    var description: String? = null
    var published = false

    constructor()

    constructor(title: String?, description: String?, published: Boolean) {
        this.title = title
        this.description = description
        this.published = published
    }
}


