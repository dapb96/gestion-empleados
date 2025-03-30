package com.prueba.gestionempleados.employees.services;


import com.prueba.gestionempleados.employees.domain.Employee;
import com.prueba.gestionempleados.employees.infrastructure.api.dtos.CreateEmployeeRequestDto;
import com.prueba.gestionempleados.employees.infrastructure.api.dtos.DepartmentEmployeeRequestDto;
import com.prueba.gestionempleados.employees.infrastructure.api.dtos.UpdateEmployeeRequestDto;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee createEmployee(CreateEmployeeRequestDto requestDto);

    List<Employee> listEmployeesbyDepartment(Long departmentId) throws BadRequestException;

    Optional<Employee> getEmployeesByDepartment(DepartmentEmployeeRequestDto requestDto);

    void deleteEmployee(DepartmentEmployeeRequestDto requestDto);

    Employee updateEmployee(DepartmentEmployeeRequestDto requestDto, UpdateEmployeeRequestDto updateRequestDto);

    Employee updateDepartmentEmployee(DepartmentEmployeeRequestDto requestDto);

}
