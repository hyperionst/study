package com.hyperion.springdata.jdsl

import org.springframework.stereotype.Repository


/**
 * Spring batch가 있어야 될듯 한데?
 */
@Repository
class JdslExample {
//    @Autowired
//    lateinit var queryProviderFactory: KotlinJdslQueryProviderFactory
//
//    val queryProvider = queryProviderFactory.create {
//        select(
//            path(Book::isbn)
//        ).from(
//            entity(Book::class),
//        )
//    }
//
//    fun exam(){
//        JpaCursorItemReaderBuilder<Isbn>()
//            .entityManagerFactory(entityManagerFactory)
//            .queryProvider(queryProvider)
//            .saveState(false)
//            .build()
//    }

}