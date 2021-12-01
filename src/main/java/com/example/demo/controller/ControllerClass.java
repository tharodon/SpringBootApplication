package com.example.demo.controller;

import com.example.demo.components.DAOProject;
import com.example.demo.components.Project;
import com.example.demo.components.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Component
@ComponentScan("com.example.demo")
@Controller
@RequestMapping("/projects")
public class ControllerClass {
    private final DAOProject daoProject;

    @Autowired
    public ControllerClass(DAOProject daoProject) {
        this.daoProject = daoProject;
    }
    @GetMapping("/all")
    public String allProjects(Model model){
        model.addAttribute("projects", daoProject.allProjects());
        return "showAll";
    }

    @PostMapping
    public String addAproject(@ModelAttribute("project") @Valid Project project, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "redirect:/projects/new";
        System.out.println(project.getName());
        System.out.println("I'm here1!");
        if (daoProject.save(project) == 1)
            return "redirect:/projects/new";
        return "redirect:/projects/all";
    }

    @GetMapping("/new")
    public String newProject(Model model){
        model.addAttribute("project", new Project());
        return "new";
    }
    @GetMapping("/{id}")
    public String showProject(@PathVariable("id") int id, Model model, HttpServletResponse response) throws IOException {
        Project project = daoProject.show(id);
        if (project == null)
            return "redirect:/projects/all";
        else
            model.addAttribute("project", project);
        return "showId";
    }

    @PostMapping("/{id}/patch")
    public String patch(@PathVariable("id") int id, @ModelAttribute("project") Project project){
        System.out.println(project);
        System.out.println("UPDATE");
        daoProject.update(project, id);
        return "redirect:/projects/all";
    }
    @PostMapping("/{id}")
    public String dlproject(@PathVariable("id") int id){
        daoProject.delete(id);
        return "redirect:/projects/all";
    }
    @GetMapping("/{id}/patch")
    public String upProj(@PathVariable("id") int id, Model model){
        model.addAttribute("project", daoProject.show(id));
        return "updatePrjct";
    }

    @GetMapping("/{id}/tasks")
    public String showTasks(@PathVariable ("id") int id, Model model){
        Project project = daoProject.show(id);
        if (project.getTasks().isEmpty()){
            System.out.println("TASKS null");
            model.addAttribute("project", daoProject.show(id));
            return "spacetasks";
        }
        model.addAttribute("tasks", project.getTasks());
        return "projectTasks";
    }

    @GetMapping("/{id}/tasks/addtask")
    public String newTask(@PathVariable("id") int id, Model model){
        System.out.println(daoProject.show(id) + " this is project");
        model.addAttribute("tasks", new Tasks(daoProject.show(id)));
        return "newtask";
    }

    @PostMapping("/all/{id}/newtask")
    public String addtask(@PathVariable("id") int id, @ModelAttribute("tasks") Tasks task){
        System.out.println(task.getName());
        task.setProject(daoProject.show(id));
        daoProject.save(task);
        System.out.println(task.getProject());
        return "redirect:/projects/all";

    }

    @GetMapping("/google")
    public void goog(HttpServletResponse response) throws IOException {
        response.sendRedirect("https://www.google.ru/");
    }
}
