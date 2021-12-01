package com.example.demo.components;

import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DAOProject {
    private ProjectRepository projectRepository;
    private TasksRepository tasksRepository;

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Autowired
    public void setTasksRepository(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public DAOProject() {

    }
    public List<Project> allProjects(){
        return projectRepository.findAll();
    }

    public int save(Project project){
        try{
            projectRepository.save(project);
        }catch (Exception e){
            System.out.println(project.getName() + " was created");
            return 1;
        }
        return 0;
    }

    public int saveTask(Tasks task){
        try{
            tasksRepository.save(task);
        }catch (Exception e){
            System.out.println(task.getName() + " was created");
            return 1;
        }
        return 0;
    }

    public Project show(int id){
        return projectRepository.getById(id);
    }
    public void delete(int id){
        projectRepository.delete(projectRepository.getById(id));
    }
    public void update(Project project, int id){
        projectRepository.save(project);
    }

}
