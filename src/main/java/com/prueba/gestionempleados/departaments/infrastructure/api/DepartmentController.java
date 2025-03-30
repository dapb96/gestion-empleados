package com.prueba.gestionempleados.departaments.infrastructure.api;

import com.prueba.gestionempleados.departaments.domain.Department;
import com.prueba.gestionempleados.departaments.infrastructure.api.dto.CreateDepartmentDto;
import com.prueba.gestionempleados.departaments.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody CreateDepartmentDto requestDto) {
        Department department = departmentService.createDepartment(requestDto.getName());
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }
}
