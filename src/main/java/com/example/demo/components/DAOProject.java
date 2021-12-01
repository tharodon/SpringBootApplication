package com.example.demo.components;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Component
public class DAOProject {
    private Session session;

    public DAOProject() {

    }
    @PostConstruct
    public void init() {
        System.out.println("this message from init");
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Project.class).buildSessionFactory();
        for (int i = 0; i < 30; i++){
            this.session = sessionFactory.getCurrentSession();
            Project project = new Project("project number #" + i);
            this.session.beginTransaction();
            try{
                session.save(project);
                session.getTransaction().commit();

                for(int count = 1; count <= 10; count++){
                    this.session = sessionFactory.getCurrentSession();
                    Tasks tasks = new Tasks("task number " + count + " for project#" + i, project);
                    this.session.beginTransaction();
                    session.save(tasks);
                    session.getTransaction().commit();
                }




            }catch (Exception e){
                System.out.println("uniq project");
                this.session.close();
            }
        }
    }
    @PreDestroy
    public void preDestroy(){
        System.out.println("closing bean from preDestroy");
    }
    public List<Project> allProjects(){
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Project.class).buildSessionFactory();
        this.session = sessionFactory.getCurrentSession();
        this.session.beginTransaction();
        List<Project> projectList = session.createQuery("from Project").getResultList();
        return projectList;
    }

    public <T> int save(T project){
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Project.class).buildSessionFactory();
        this.session = sessionFactory.getCurrentSession();
        this.session.beginTransaction();
        try{
            session.save(project);
        }catch (Exception e){
            System.out.println("Uniq project");
            return (1);
        }
        session.getTransaction().commit();
        return 0;
    }

    public int saveTask(Tasks task, Project project){
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Tasks.class).buildSessionFactory();
        this.session = sessionFactory.getCurrentSession();
        this.session.beginTransaction();
        try{
            session.save(task);
        }catch (Exception e){
            System.out.println("Uniq project");
            return (1);
        }
        session.getTransaction().commit();
        return 0;
    }

    public Project show(int id){
        Project project = null;
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Project.class).buildSessionFactory();
        this.session = sessionFactory.getCurrentSession();
        this.session.beginTransaction();
        project = session.get(Project.class, id);
        return project;
    }
    public void delete(int id){
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Project.class).buildSessionFactory();
        Project project = null;
        this.session = sessionFactory.getCurrentSession();
        this.session.beginTransaction();
        project = session.get(Project.class, id);
        session.remove(project);
        session.getTransaction().commit();
    }
    public void update(Project project, int id){
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Project.class).buildSessionFactory();
        Project projectNew = null;
        this.session = sessionFactory.getCurrentSession();
        this.session.beginTransaction();
        projectNew = session.get(Project.class, id);
        projectNew.setName(project.getName());
        session.getTransaction().commit();
    }

}
