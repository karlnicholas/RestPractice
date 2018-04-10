package employeedetail.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import employeedetail.item.SparseEmployeeDetailItem;
import employeedetail.model.EmployeeDetail;

public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetail, Integer> {
    public List<SparseEmployeeDetailItem> findAllBy(Pageable pageRequest);
}
