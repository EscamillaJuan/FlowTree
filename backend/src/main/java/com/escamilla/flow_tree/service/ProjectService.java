package com.escamilla.flow_tree.service;

import com.escamilla.flow_tree.model.entity.Project;
import com.escamilla.flow_tree.model.entity.User;
import com.escamilla.flow_tree.model.repository.ProjectRepository;
import com.escamilla.flow_tree.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<Project> getAllProjectsByUser(UserDetails user) {
        if (user == null) return null;
        return projectRepository.findByOwnerEmail(user.getUsername());
    }

    public Project saveProject(Project project, String email) {
        if (project == null) return null;
        Optional<User> owner = userRepository.findByEmail(email);
        if (owner.isEmpty()) return null;
        project.setOwner(owner.get());
        project.setCreatedOn(new Date());
        return projectRepository.save(project);
    }

    public boolean isOwner(Long projectId, String email) {
        Optional<Project> project = projectRepository.findById(projectId);
        Optional<User> user = userRepository.findByEmail(email);
        if (project.isEmpty() || user.isEmpty()) return false;
        return project.get().getOwner() == user.get();
    }

    public Optional<Project> getById(Long projectId) {
        if (projectId == null) return Optional.empty();
        return projectRepository.findById(projectId);
    }

    public void deleteById(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}
