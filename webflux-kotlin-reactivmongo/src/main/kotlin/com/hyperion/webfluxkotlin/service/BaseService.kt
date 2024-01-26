package com.hyperion.webfluxkotlin.service


import com.hyperion.webfluxkotlin.entity.BaseDocument
import com.hyperion.webfluxkotlin.repository.BaseRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux


@Service
class BaseService(val baseRepository: BaseRepository) {


    /**
     *
     */
    fun getAll() : Flux<BaseDocument> = baseRepository.findAll()

    /**
     *
     */
    fun getDataByBaseId(baseId : String) : Flux<BaseDocument> = baseRepository.findByBaseId(baseId)


}