package employeeaddress.service;

import org.springframework.data.jpa.repository.JpaRepository;

import employeeaddress.model.EmployeeAddress;

public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddress, Integer> {

}
