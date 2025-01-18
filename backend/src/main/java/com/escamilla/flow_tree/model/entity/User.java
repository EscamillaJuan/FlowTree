package com.escamilla.flow_tree.model.entity;

import com.escamilla.flow_tree.model.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String email;

    private String password;
    private String firstname;
    private String lastname;
    private Date createdOn;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("user-owned-projects")
    private Set<Project> ownedProjects;

    @OneToMany(mappedBy = "collaborators")
    private Set<Project> collaboratingProjects;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("user-created-tasks")
    private Set<Task> createdTasks;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("user-assigned-tasks")
    private Set<Task> assignedTasks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addProject(Project project) {
        if (project == null) {
            return;
        }
        this.ownedProjects.add(project);
        project.setOwner(this);
    }

    public void removeProject(Project project) {
        if (project == null) {
            return;
        }
        this.ownedProjects.remove(project);
        project.setOwner(new User());
    }

    public void addSharedProject(Project project) {
        if (project == null) {
            return;
        }
        this.collaboratingProjects.add(project);
        project.addCollaborator(this);
    }

    public void removeSharedProject(Project project) {
        if (project == null) {
            return;
        }
        this.collaboratingProjects.remove(project);
        project.removeCollaborator(this);
    }

    private void addTask(Task task) {
        this.createdTasks.add(task);
        task.setCreator(this);
    }

    private void removeTask(Task task) {
        this.createdTasks.remove(task);
    }

    private void addAssignedTask(Task task) {
        this.assignedTasks.add(task);
        task.setAssignee(this);
    }

    private void removeAssignedTask(Task task) {
        this.assignedTasks.remove(task);
        task.setAssignee(null);
    }

}
