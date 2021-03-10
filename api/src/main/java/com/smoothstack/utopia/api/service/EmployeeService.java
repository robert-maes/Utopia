package com.smoothstack.utopia.api.service;

import com.smoothstack.utopia.api.dao.EmployeeDao;
import com.smoothstack.utopia.api.dto.CreateEmployeeDto;
import com.smoothstack.utopia.api.dto.UpdateEmployeeDto;
import com.smoothstack.utopia.api.exception.DuplicateUsernameException;
import com.smoothstack.utopia.api.exception.EmployeeNotFoundException;
import com.smoothstack.utopia.api.model.Employee;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  private final EmployeeDao employeeDao;

  @Autowired
  public EmployeeService(EmployeeDao employeeDao) {
    this.employeeDao = employeeDao;
  }

  public List<Employee> getAllEmployees() {
    return employeeDao.findAll();
  }

  public Employee getEmployee(Long employeeId) {
    Optional<Employee> employeeOptional = employeeDao.findById(employeeId);
    if (employeeOptional.isEmpty()) {
      throw new EmployeeNotFoundException();
    }
    return employeeOptional.get();
  }

  public void createEmployee(CreateEmployeeDto createEmployeeDto) {
    Optional<Employee> employeeOptional = employeeDao.findEmployeeByUsername(
      createEmployeeDto.getUsername()
    );
    if (employeeOptional.isPresent()) {
      throw new DuplicateUsernameException();
    }
    Employee employee = new Employee();
    employee.setEmail(createEmployeeDto.getEmail());
    employee.setFamilyName(createEmployeeDto.getFamilyName());
    employee.setGivenName(createEmployeeDto.getGivenName());
    employee.setUsername(createEmployeeDto.getUsername());
    employee.setPhoneNumber(createEmployeeDto.getPhoneNumber());
    employeeDao.save(employee);
  }

  @Transactional
  public void updateEmployee(
    Long employeeId,
    UpdateEmployeeDto updateEmployeeDto
  ) {
    Optional<Employee> employeeToUpdateOptional = employeeDao.findById(
      employeeId
    );
    if (employeeToUpdateOptional.isEmpty()) {
      throw new EmployeeNotFoundException();
    }
    Employee employeeToUpdate = employeeToUpdateOptional.get();
    if (updateEmployeeDto.getEmail().isPresent()) {
      employeeToUpdate.setEmail((updateEmployeeDto.getEmail().get()));
    }
    if (updateEmployeeDto.getFamilyName().isPresent()) {
      employeeToUpdate.setFamilyName(updateEmployeeDto.getFamilyName().get());
    }
    if (updateEmployeeDto.getGivenName().isPresent()) {
      employeeToUpdate.setGivenName(updateEmployeeDto.getGivenName().get());
    }
    if (updateEmployeeDto.getPhoneNumber().isPresent()) {
      employeeToUpdate.setPhoneNumber(updateEmployeeDto.getPhoneNumber().get());
    }
    employeeDao.save(employeeToUpdate);
  }

  public void deleteEmployee(Long employeeId) {
    Optional<Employee> employeeOptional = employeeDao.findById(employeeId);
    if (employeeOptional.isEmpty()) {
      throw new EmployeeNotFoundException();
    }
    employeeDao.delete(employeeOptional.get());
  }
}
