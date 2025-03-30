package com.prueba.gestionempleados.departaments.services.impl;

import com.prueba.gestionempleados.departaments.domain.Department;
import com.prueba.gestionempleados.departaments.infrastructure.database.repositories.DepartmentRepository;
import com.prueba.gestionempleados.departaments.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department createDepartment(String name) {
        Department department = new Department(null, name);
        return Department.fromEntity(departmentRepository.save(department.toJpaEntity()));
    }
}
