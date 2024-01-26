package com.hyperion.apifirst.controller

import com.hyperion.apifirst.entitiyModel.Todo
import com.hyperion.apifirst.repository.TodoRepository
import com.hyperion.apifirst.responsModel.ResponseModel
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TodoController(private val todoRepository: TodoRepository) {
    @GetMapping("/get_todo")
    fun getTodo() = ResponseModel("0", "", todoRepository.findAll())

    @PostMapping("/add_todo")
    fun addTodo(@Valid @RequestBody todo: Todo) =
        ResponseEntity.ok().body(ResponseModel("0", "", todoRepository.save(todo)))

    @PostMapping("/add_todos")
    fun addTodos(@Valid @RequestBody todos: List<Todo>) =
        ResponseEntity.ok().body(ResponseModel("0", "", todoRepository.saveAll(todos)))

    @PostMapping("/edit_todo")
    fun editTodo(@Valid @RequestBody todo: Todo): ResponseEntity<ResponseModel> {
        val optionalTodo = todoRepository.findById(todo.id)
        if (optionalTodo.isPresent) {
            return ResponseEntity.ok().body(ResponseModel("0", "edit successful", todoRepository.save(todo)))
        } else {
            return ResponseEntity.badRequest().body(ResponseModel("1", "invalid id"))
        }
    }

    @DeleteMapping("/delete_todo/{id}")
    fun deleteTodo(@PathVariable id: Int): ResponseEntity<ResponseModel> {
        val optionalTodo = todoRepository.findById(id)
        if (optionalTodo.isPresent) {
            todoRepository.delete(optionalTodo.get())
            return ResponseEntity.ok().body(ResponseModel("0", "delete successful"))
        } else {
            return ResponseEntity.badRequest().body(ResponseModel("1", "invalid id"))
        }

    }




}