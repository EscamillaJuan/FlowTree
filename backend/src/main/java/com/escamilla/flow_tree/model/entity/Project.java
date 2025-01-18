package com.escamilla.flow_tree.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonBackReference("user-owned-projects")
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "project_collaborators",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> collaborators;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("project-tasks")
    private List<Task> tasks;

    public void addTask(Task task) {
        this.tasks.add(task);
        task.setProject(this);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
        task.setProject(null);
    }

    public void addCollaborator(User user) {
        this.collaborators.add(user);
        user.addSharedProject(this);
    }

    public void removeCollaborator(User user) {
        this.collaborators.remove(user);
        user.removeSharedProject(this);
    }
}
