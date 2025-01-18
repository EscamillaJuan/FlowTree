package com.escamilla.flow_tree.service;

import com.escamilla.flow_tree.model.entity.Project;
import com.escamilla.flow_tree.model.entity.Task;
import com.escamilla.flow_tree.model.entity.User;
import com.escamilla.flow_tree.model.repository.ProjectRepository;
import com.escamilla.flow_tree.model.repository.TaskRepository;
import com.escamilla.flow_tree.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public Task saveTask(UserDetails userDetails, Long projectId, Task task) {
        if (userDetails == null || projectId == null) return null;
        Optional<User> creator = userRepository.findByEmail(userDetails.getUsername());
        Optional<Project> project = projectRepository.findById(projectId);
        if (creator.isEmpty() || project.isEmpty()) return null;
        task.setCreator(creator.get());
        task.setProject(project.get());
        project.get().addTask(task);
        return taskRepository.save(task);
    }

    public List<Task> getByUser(String email) {
        return taskRepository.findByCreatorEmail(email);
    }
}
