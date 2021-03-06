package employeeproject.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import project.item.SparseProjectItem;
import employeeproject.model.Project;
import project.item.ProjectItem;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    public Page<SparseProjectItem> findAllBy(Pageable pageRequest);    
    @Query("select new project.item.ProjectItem(p.projectId, p.projectName, p.techstack) from Project p where p.projectId in (select ep.employeeProjectId.projectId from EmployeeProject ep where ep.employeeProjectId.empId = :empId)")
    public List<ProjectItem> findAllByEmployeeProjectEmpId(@Param("empId") Integer empId);
}
