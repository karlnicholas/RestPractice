package project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import employeeproject.item.SparseProjectItem;
import project.item.ProjectItem;
import project.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    public Page<SparseProjectItem> findAllBy(Pageable pageRequest);
    public List<ProjectItem> findAllByProjectIdIn(Iterable<Integer> projectIds);
}
