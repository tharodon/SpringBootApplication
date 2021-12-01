package com.example.demo.components;

import javax.persistence.*;
import java.util.Locale;

@Entity
@Table(name = "tasks")
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_project")
    private Project project;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tasks(String name, Project project) {
        this.name = name;
        this.project = project;
    }

    public Tasks() {
    }

    public Tasks(Project project) {
        this.project = project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Task with " +
                "name='" + name.toUpperCase(Locale.ROOT) + '\'' +
                ", project=" + project.getName();
    }
}
