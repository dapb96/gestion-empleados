package com.prueba.gestionempleados.employees.services.impl;

import com.prueba.gestionempleados.departaments.domain.Department;
import com.prueba.gestionempleados.departaments.infrastructure.database.enitites.DepartmentJpaEntity;
import com.prueba.gestionempleados.departaments.infrastructure.database.repositories.DepartmentRepository;
import com.prueba.gestionempleados.employees.domain.Employee;
import com.prueba.gestionempleados.employees.infrastructure.api.dtos.CreateEmployeeRequestDto;
import com.prueba.gestionempleados.employees.infrastructure.api.dtos.DepartmentEmployeeRequestDto;
import com.prueba.gestionempleados.employees.infrastructure.api.dtos.UpdateEmployeeRequestDto;
import com.prueba.gestionempleados.employees.infrastructure.database.entities.EmployeeJPAEntity;
import com.prueba.gestionempleados.employees.infrastructure.database.respositories.EmployeeRepository;
import com.prueba.gestionempleados.employees.services.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(CreateEmployeeRequestDto requestDto) {

        if (requestDto.getName() == null || requestDto.getName().trim().isEmpty() ||
                requestDto.getLastName() == null || requestDto.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre y el apellido del empleado son obligatorios.");
        }

        if (requestDto.getDepartamentoId() == null) {
            throw new IllegalArgumentException("El ID del departamento es obligatorio.");
        }

        Department department = departmentRepository.findById(requestDto.getDepartamentoId())
                .map(Department::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el departamento con ID: " + requestDto.getDepartamentoId()));

        Employee employee = new Employee(null, requestDto.getName(), requestDto.getLastName(), department);

        return Employee.fromJpaEntity(employeeRepository.save(employee.toJpaEntity()));
    }

    @Override
    public List<Employee> listEmployeesbyDepartment(Long departmentoId) throws BadRequestException {

        if (departmentoId == null) {
            throw new BadRequestException("El ID del departamento es obligatorio para la busqueda de los empleados.");
        }

        return employeeRepository.findBydepartmentId(departmentoId)
                .stream().map(Employee::fromJpaEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<Employee> getEmployeesByDepartment(DepartmentEmployeeRequestDto requestDto) {
        return employeeRepository.findBydepartmentIdAndId(requestDto.getDepartmentId(), requestDto.getEmployeeId()).map(Employee::fromJpaEntity);
    }

    @Override
    @Transactional
    public void deleteEmployee(DepartmentEmployeeRequestDto requestDto) {
        Optional<EmployeeJPAEntity> empleadoJpaEntityOptional = employeeRepository.findBydepartmentIdAndId(requestDto.getDepartmentId(), requestDto.getEmployeeId());
        empleadoJpaEntityOptional.ifPresent(employeeRepository::delete);
        if (empleadoJpaEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("No se encontró el empleado con ID " + requestDto.getEmployeeId() + " en el departamento con ID " + requestDto.getDepartmentId());
        }
    }

    @Override
    @Transactional
    public Employee updateEmployee(DepartmentEmployeeRequestDto requestDto, UpdateEmployeeRequestDto updateRequestDto) {
        EmployeeJPAEntity empleadoJpaEntity = employeeRepository.findBydepartmentIdAndId(requestDto.getDepartmentId(), requestDto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el empleado con ID " + requestDto.getEmployeeId() + " en el departamento con ID " + requestDto.getDepartmentId()));
        return Employee.fromJpaEntity(empleadoJpaEntity);
    }

    @Override
    @Transactional
    public Employee updateDepartmentEmployee(DepartmentEmployeeRequestDto requestDto) {
        if (requestDto.getDepartmentId() == null) {
            throw new IllegalArgumentException("El ID del nuevo departamento es obligatorio.");
        }

        EmployeeJPAEntity empleadoJpaEntity = employeeRepository.findById(requestDto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el empleado con ID: " + requestDto.getEmployeeId()));

        DepartmentJpaEntity newDepartmentJpaEntity = departmentRepository.findById(requestDto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el departamento con ID: " + requestDto.getDepartmentId()));

        empleadoJpaEntity.setDepartment(newDepartmentJpaEntity);
        return Employee.fromJpaEntity(employeeRepository.save(empleadoJpaEntity));
    }

}
