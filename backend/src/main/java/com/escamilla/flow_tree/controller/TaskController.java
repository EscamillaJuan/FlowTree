package com.escamilla.flow_tree.controller;

import com.escamilla.flow_tree.model.entity.Task;
import com.escamilla.flow_tree.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Task>> getTasksByUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(taskService.getByUser(userDetails.getUsername()));
    }
}
