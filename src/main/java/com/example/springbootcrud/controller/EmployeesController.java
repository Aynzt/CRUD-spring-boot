package com.example.springbootcrud.controller;

import java.math.BigDecimal;
import java.util.*;

import com.example.springbootcrud.model.Position;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springbootcrud.model.Employee;
import com.example.springbootcrud.service.EmployeeService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("employees")

public class EmployeesController {

  private final EmployeeService employeeService;

  public EmployeesController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("{id}")
  public ResponseEntity<Employee> get(@PathVariable UUID id) {
    return employeeService.get(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound()
        .build());
  }


  @GetMapping
  public ResponseEntity<List<Employee>> list(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName,
                                             @RequestParam(required = false)Position position, @RequestParam(required = false) Date startDate,
                                             @RequestParam(required = false)BigDecimal salary, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "id") String field) {
    List<Employee> list = employeeService.list(firstName, lastName, position, startDate, salary, page, pageSize, field);
    return new ResponseEntity<List<Employee>>(list, new HttpHeaders(), HttpStatus.OK);
  }

  @PostMapping
  public UUID save(@RequestBody Employee employee) {
    return employeeService.save(employee)
      .getId();
  }

  @PutMapping("{id}")
  public Employee edit(@PathVariable UUID id, @RequestBody Employee employee) {
    employee.setId(id);
    return employeeService.save(employee);
  }

  @PostMapping("/delete")
  public void remove(@RequestBody List<UUID> ids) {
    employeeService.removeAll(ids);
  }
}
