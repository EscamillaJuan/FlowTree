package com.escamilla.flow_tree.controller;

import com.escamilla.flow_tree.model.entity.Project;
import com.escamilla.flow_tree.model.repository.UserRepository;
import com.escamilla.flow_tree.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService, UserRepository userRepository) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<Project>> getProjectsByUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projectService.getAllProjectsByUser(userDetails));
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Project project) {
        return ResponseEntity.ok(projectService.saveProject(project, userDetails.getUsername()));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Optional<Project>> getProjectById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long projectId) {
        if (!projectService.isOwner(projectId, userDetails.getUsername())) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(projectService.getById(projectId));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Project> removeProject(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long projectId) {
        if (!projectService.isOwner(projectId, userDetails.getUsername())) return ResponseEntity.status(403).build();
        projectService.deleteById(projectId);
        return ResponseEntity.ok().build();
    }
}
