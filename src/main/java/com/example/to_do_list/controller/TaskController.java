package com.example.to_do_list.controller;

import com.example.to_do_list.model.Task;
import com.example.to_do_list.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")  // базовый путь: http://localhost:8080/tasks
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET http://localhost:8080/tasks/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskInfo(@PathVariable Long id) {
        return taskService.findTask(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET http://localhost:8080/tasks
    // GET http://localhost:8080/tasks?categoryId=1
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(name = "categoryId", required = false) Long categoryId
    ) {
        List<Task> tasks;
        if (categoryId != null) {
            tasks = taskService.findByCategoryId(categoryId);
        } else {
            tasks = taskService.getAllTasks();
        }
        return ResponseEntity.ok(tasks);
    }

    // POST http://localhost:8080/tasks
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task created = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT http://localhost:8080/tasks/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Task> editTask(@PathVariable Long id,
                                         @RequestBody Task task) {
        task.setId(id);
        Task updatedTask = taskService.editTask(task);
        if (updatedTask == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTask);
    }

    // DELETE http://localhost:8080/tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
