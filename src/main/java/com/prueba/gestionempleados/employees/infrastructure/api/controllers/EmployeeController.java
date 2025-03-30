package com.prueba.gestionempleados.employees.infrastructure.api.controllers;

import com.prueba.gestionempleados.employees.domain.Employee;
import com.prueba.gestionempleados.employees.infrastructure.api.dtos.CreateEmployeeRequestDto;
import com.prueba.gestionempleados.employees.infrastructure.api.dtos.DepartmentEmployeeRequestDto;
import com.prueba.gestionempleados.employees.infrastructure.api.dtos.UpdateEmployeeRequestDto;
import com.prueba.gestionempleados.employees.services.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody CreateEmployeeRequestDto requestDto) {
        Employee newEmployee = employeeService.createEmployee(requestDto);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Employee>> listEmployeesbyDepartment(@PathVariable Long departmentId) {
        try {
            List<Employee> empleados = employeeService.listEmployeesbyDepartment(departmentId);
            return new ResponseEntity<>(empleados, HttpStatus.OK);
        } catch (BadRequestException bre) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{employeeId}/by-department/{departmentId}")
    public ResponseEntity<Employee> getEmployeesByDepartment(@PathVariable Long employeeId, @PathVariable Long departmentId) {
        DepartmentEmployeeRequestDto requestDto = new DepartmentEmployeeRequestDto(employeeId, departmentId);
        Optional<Employee> empleado = employeeService.getEmployeesByDepartment(requestDto);
        return empleado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteEmployee(@RequestBody DepartmentEmployeeRequestDto requestDto) {
        try {
            employeeService.deleteEmployee(requestDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{departmentId}/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long departmentId, @PathVariable long employeeId, @RequestBody UpdateEmployeeRequestDto requestDto) {
        try {
            Employee empleadoActualizado = employeeService.updateEmployee(new DepartmentEmployeeRequestDto(employeeId,departmentId), requestDto);
            return new ResponseEntity<>(empleadoActualizado, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/department")
    public ResponseEntity<Employee> updateDepartmentEmployee(@RequestBody DepartmentEmployeeRequestDto requestDto) {
        try {
            Employee empleadoActualizado = employeeService.updateDepartmentEmployee(requestDto);
            return new ResponseEntity<>(empleadoActualizado, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
