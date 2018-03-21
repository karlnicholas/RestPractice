package employeedetail.service;

import org.springframework.data.jpa.repository.JpaRepository;

import employeedetail.model.EmployeeDetail;

public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetail, Integer> {

}
