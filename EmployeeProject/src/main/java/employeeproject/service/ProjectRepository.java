package employeeproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import employeeproject.item.SparseProjectItem;
import employeeproject.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    public Page<SparseProjectItem> findAllBy(Pageable pageRequest);
}
