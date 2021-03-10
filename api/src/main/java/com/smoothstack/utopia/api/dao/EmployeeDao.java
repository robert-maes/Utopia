package com.smoothstack.utopia.api.dao;

import com.smoothstack.utopia.api.model.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {
  Optional<Employee> findEmployeeByUsername(String username);
}
