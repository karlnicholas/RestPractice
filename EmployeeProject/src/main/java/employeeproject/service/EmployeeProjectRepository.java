package employeeproject.service;

import org.springframework.data.jpa.repository.JpaRepository;

import employeeproject.model.EmployeeProject;
import employeeproject.model.EmployeeProjectId;

public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, EmployeeProjectId> {
}
