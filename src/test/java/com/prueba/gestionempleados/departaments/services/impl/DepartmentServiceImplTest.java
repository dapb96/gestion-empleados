package com.prueba.gestionempleados.departaments.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.prueba.gestionempleados.departaments.domain.Department;
import com.prueba.gestionempleados.departaments.infrastructure.database.enitites.DepartmentJpaEntity;
import com.prueba.gestionempleados.departaments.infrastructure.database.repositories.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Test
    void createDepartment_ValidName_ReturnsCreatedDepartment() {
        // Arrange
        String departmentName = "Technology";
        Department departmentToSave = new Department(null, departmentName);
        DepartmentJpaEntity savedEntity = new DepartmentJpaEntity(1L, departmentName);
        Department expectedDepartment = new Department(1L, departmentName);

        when(departmentRepository.save(any(DepartmentJpaEntity.class))).thenReturn(savedEntity);

        // Act
        Department actualDepartment = departmentService.createDepartment(departmentName);

        // Assert
        assertNotNull(actualDepartment);
        assertEquals(expectedDepartment.getId(), actualDepartment.getId());
        assertEquals(expectedDepartment.getName(), actualDepartment.getName());
    }

    @Test
    void createDepartment_EmptyName_ReturnsCreatedDepartmentWithEmptyName() {
        // Arrange
        String departmentName = "";
        Department departmentToSave = new Department(null, departmentName);
        DepartmentJpaEntity savedEntity = new DepartmentJpaEntity(1L, departmentName);
        Department expectedDepartment = new Department(1L, departmentName);

        when(departmentRepository.save(any(DepartmentJpaEntity.class))).thenReturn(savedEntity);

        // Act
        Department actualDepartment = departmentService.createDepartment(departmentName);

        // Assert
        assertNotNull(actualDepartment);
        assertEquals(expectedDepartment.getId(), actualDepartment.getId());
        assertEquals(expectedDepartment.getName(), actualDepartment.getName());
    }

    @Test
    void createDepartment_NullName_ReturnsCreatedDepartmentWithNullName() {
        // Arrange
        String departmentName = null;
        Department departmentToSave = new Department(null, departmentName);
        DepartmentJpaEntity savedEntity = new DepartmentJpaEntity(1L, departmentName);
        Department expectedDepartment = new Department(1L, departmentName);

        when(departmentRepository.save(any(DepartmentJpaEntity.class))).thenReturn(savedEntity);

        // Act
        Department actualDepartment = departmentService.createDepartment(departmentName);

        // Assert
        assertNotNull(actualDepartment);
        assertEquals(expectedDepartment.getId(), actualDepartment.getId());
        assertEquals(expectedDepartment.getName(), actualDepartment.getName());
    }

}
