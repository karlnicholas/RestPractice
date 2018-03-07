package employeeproject.service;

import org.springframework.data.jpa.repository.JpaRepository;

import employeeproject.model.EmployeeProject;

public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {

}
