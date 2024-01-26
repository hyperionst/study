package com.hyperion.apifirst.responsModel

import com.hyperion.apifirst.entitiyModel.Todo

data class ResponseModel(
    val errorCode: String,
    val errorMessage: String,
    val data:List<Todo> = listOf()
){
    constructor(
        errorCode: String,
        errorMessage: String,
        todo: Todo
    ): this(errorCode, errorMessage, listOf(todo))
}
