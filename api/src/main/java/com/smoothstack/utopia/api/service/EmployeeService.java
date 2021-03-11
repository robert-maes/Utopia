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

/**
 * @author Rob Maes
 * Mar 11 2021
 */
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
    // try to find the requested employee
    Optional<Employee> employeeOptional = employeeDao.findById(employeeId);
    // if the employee does not exist, throw a 404
    if (employeeOptional.isEmpty()) {
      throw new EmployeeNotFoundException();
    }
    // otherwise, return the employee
    return employeeOptional.get();
  }

  public void createEmployee(CreateEmployeeDto createEmployeeDto) {
    // try to find an existing employee with the new employees username
    Optional<Employee> employeeOptional = employeeDao.findEmployeeByUsername(
      createEmployeeDto.getUsername()
    );
    // if an employee with this username already exists, throw an error
    if (employeeOptional.isPresent()) {
      throw new DuplicateUsernameException();
    }
    // create the new employee
    Employee employee = new Employee();
    employee.setEmail(createEmployeeDto.getEmail());
    employee.setFamilyName(createEmployeeDto.getFamilyName());
    employee.setGivenName(createEmployeeDto.getGivenName());
    employee.setUsername(createEmployeeDto.getUsername());
    employee.setPhoneNumber(createEmployeeDto.getPhoneNumber());
    // save the employee
    employeeDao.save(employee);
  }

  @Transactional
  public void updateEmployee(
    Long employeeId,
    UpdateEmployeeDto updateEmployeeDto
  ) {
    // try to find the employee to update
    Optional<Employee> employeeToUpdateOptional = employeeDao.findById(
      employeeId
    );
    // if the employee does not exist, throw an error
    if (employeeToUpdateOptional.isEmpty()) {
      throw new EmployeeNotFoundException();
    }
    // otherwise, update the specified fields
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
    // save the updated employee
    employeeDao.save(employeeToUpdate);
  }

  public void deleteEmployee(Long employeeId) {
    // try to find the employee to delete
    Optional<Employee> employeeOptional = employeeDao.findById(employeeId);
    // if the employee does not exist, throw a 404
    if (employeeOptional.isEmpty()) {
      throw new EmployeeNotFoundException();
    }
    // otherwise, delete the employee
    employeeDao.delete(employeeOptional.get());
  }
}
