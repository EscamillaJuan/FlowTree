package com.escamilla.flow_tree.controller;

import com.escamilla.flow_tree.model.entity.Project;
import com.escamilla.flow_tree.model.entity.Task;
import com.escamilla.flow_tree.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<Task> createTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long projectId,
            @RequestBody Task task
    ) {
        return ResponseEntity.ok(taskService.saveTask(userDetails, projectId, task));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Task> deleteTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long taskId
    ) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long taskId,
            @RequestBody Task taskDetails
    ) {
        Optional<Task> task = taskService.getById(taskId);
        return task.map(value ->
                        ResponseEntity.ok(taskService.update(taskDetails, value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
