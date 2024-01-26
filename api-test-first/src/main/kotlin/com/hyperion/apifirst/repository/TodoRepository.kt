package com.hyperion.apifirst.repository

import com.hyperion.apifirst.entitiyModel.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Int>{
}