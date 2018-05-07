package employeeproject.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import employeeproject.model.Project;
import project.item.ProjectItem;

    public interface ProjectRepository extends JpaRepository<Project, Integer> {
        public Page<ProjectItem> findAllBy(Pageable pageRequest);
        @Query("select p from Project p where p.projectId in (select ep.employeeProjectId.projectId from EmployeeProject ep where ep.employeeProjectId.empId = :empId)")
        public List<Project> findAllByEmployeeProjectEmpId(@Param("empId") Integer empId);
        @Query("select p from Project p")
        public List<ProjectItem> findAllForTest();
        public List<ProjectItem> findAllBy();
    }
