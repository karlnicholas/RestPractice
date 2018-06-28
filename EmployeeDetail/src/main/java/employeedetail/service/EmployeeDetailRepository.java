package employeedetail.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import employeedetail.item.SparseEmployeeDetailItem;
import employeedetail.model.EmployeeDetail;

public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetail, Integer> {
    public Page<SparseEmployeeDetailItem> findAllBy(Pageable pageRequest);
}
