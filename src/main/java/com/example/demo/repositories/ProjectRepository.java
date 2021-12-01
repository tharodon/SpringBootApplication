package com.example.demo.repositories;

import com.example.demo.components.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Query("select b from Project b where b.name like '%upda%'")
    List<Project> findProjectBySubstring();
}
