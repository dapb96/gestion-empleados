package com.prueba.gestionempleados.employees.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.prueba.gestionempleados.departaments.domain.Department;
import com.prueba.gestionempleados.departaments.infrastructure.database.enitites.DepartmentJpaEntity;
import com.prueba.gestionempleados.departaments.infrastructure.database.repositories.DepartmentRepository;
import com.prueba.gestionempleados.employees.domain.Employee;
import com.prueba.gestionempleados.employees.infrastructure.api.dtos.CreateEmployeeRequestDto;
import com.prueba.gestionempleados.employees.infrastructure.api.dtos.DepartmentEmployeeRequestDto;
import com.prueba.gestionempleados.employees.infrastructure.api.dtos.UpdateEmployeeRequestDto;
import com.prueba.gestionempleados.employees.infrastructure.database.entities.EmployeeJPAEntity;
import com.prueba.gestionempleados.employees.infrastructure.database.respositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void createEmployee_ValidRequest_ReturnsCreatedEmployee() {
        // given
        CreateEmployeeRequestDto requestDto = new CreateEmployeeRequestDto("John", "Doe", 1L);
        DepartmentJpaEntity departmentJpaEntity = new DepartmentJpaEntity(1L, "Technology");
        Department department = new Department(1L, "Technology");
        EmployeeJPAEntity savedEmployeeJpaEntity = new EmployeeJPAEntity(1L, "John", "Doe", departmentJpaEntity);
        Employee expectedEmployee = new Employee(1L, "John", "Doe", department);

        // when
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(departmentJpaEntity));
        when(employeeRepository.save(any(EmployeeJPAEntity.class))).thenReturn(savedEmployeeJpaEntity);

        // then
        Employee actualEmployee = employeeService.createEmployee(requestDto);

        assertNotNull(actualEmployee);
        assertEquals(expectedEmployee.getId(), actualEmployee.getId());
        assertEquals(expectedEmployee.getName(), actualEmployee.getName());
        assertEquals(expectedEmployee.getLastName(), actualEmployee.getLastName());
        assertEquals(expectedEmployee.getDepartment().getId(), actualEmployee.getDepartment().getId());
    }

    @Test
    void createEmployee_MissingName_ThrowsIllegalArgumentException() {
        // given
        CreateEmployeeRequestDto requestDto = new CreateEmployeeRequestDto(null, "Doe", 1L);

        // then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(requestDto));
        verifyNoInteractions(departmentRepository);
        verifyNoInteractions(employeeRepository);
    }

    @Test
    void createEmployee_DepartmentNotFound_ThrowsEntityNotFoundException() {
        // given
        CreateEmployeeRequestDto requestDto = new CreateEmployeeRequestDto("John", "Doe", 99L);

        // when
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> employeeService.createEmployee(requestDto));
        verify(departmentRepository, times(1)).findById(99L);
        verifyNoInteractions(employeeRepository);
    }

    @Test
    void listEmployeesbyDepartment_ValidDepartmentId_ReturnsListOfEmployees() throws BadRequestException {
        // given
        Long departmentId = 1L;
        DepartmentJpaEntity departmentJpaEntity = new DepartmentJpaEntity(1L, "Technology");
        EmployeeJPAEntity employeeJpaEntity1 = new EmployeeJPAEntity(1L, "John", "Doe", departmentJpaEntity);
        EmployeeJPAEntity employeeJpaEntity2 = new EmployeeJPAEntity(2L, "Jane", "Smith", departmentJpaEntity);
        List<EmployeeJPAEntity> employeeJpaEntities = List.of(employeeJpaEntity1, employeeJpaEntity2);
        Department department = new Department(1L, "Technology");
        Employee employee1 = new Employee(1L, "John", "Doe", department);
        Employee employee2 = new Employee(2L, "Jane", "Smith", department);
        List<Employee> expectedEmployees = List.of(employee1, employee2);

        // when
        when(employeeRepository.findBydepartment_Id(departmentId)).thenReturn(employeeJpaEntities);

        // then
        List<Employee> actualEmployees = employeeService.listEmployeesbyDepartment(departmentId);

        assertNotNull(actualEmployees);
        assertEquals(expectedEmployees.size(), actualEmployees.size());
        assertEquals(expectedEmployees.get(0).getName(), actualEmployees.get(0).getName());
        assertEquals(expectedEmployees.get(1).getName(), actualEmployees.get(1).getName());
        verify(employeeRepository, times(1)).findBydepartment_Id(departmentId);
    }

    @Test
    void listEmployeesbyDepartment_NullDepartmentId_ThrowsBadRequestException() {
        // then
        assertThrows(BadRequestException.class, () -> employeeService.listEmployeesbyDepartment(null));
        verifyNoInteractions(employeeRepository);
    }
}
