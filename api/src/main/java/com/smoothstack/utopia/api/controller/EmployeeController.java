package com.smoothstack.utopia.api.controller;

import com.smoothstack.utopia.api.dto.CreateEmployeeDto;
import com.smoothstack.utopia.api.dto.UpdateEmployeeDto;
import com.smoothstack.utopia.api.model.Employee;
import com.smoothstack.utopia.api.service.EmployeeService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

  private final EmployeeService employeeService;

  @Autowired
  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping
  public List<Employee> getAllEmployees() {
    return employeeService.getAllEmployees();
  }

  @GetMapping(path = "{employeeId}")
  public Employee getEmployee(@PathVariable("employeeId") Long employeeId) {
    return employeeService.getEmployee(employeeId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createEmployee(
    @Valid @RequestBody CreateEmployeeDto createEmployeeDto
  ) {
    employeeService.createEmployee(createEmployeeDto);
  }

  @PutMapping(path = "{employeeId}")
  public void updateEmployee(
    @PathVariable("employeeId") Long employeeId,
    @Valid @RequestBody UpdateEmployeeDto updateEmployeeDto
  ) {
    employeeService.updateEmployee(employeeId, updateEmployeeDto);
  }

  @DeleteMapping(path = "{employeeId}")
  public void deleteEmployee(@PathVariable("employeeId") Long employeeId) {
    employeeService.deleteEmployee(employeeId);
  }
}
