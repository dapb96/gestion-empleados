package com.prueba.gestionempleados.employees.domain;

import com.prueba.gestionempleados.departaments.domain.Department;
import com.prueba.gestionempleados.employees.infrastructure.database.entities.EmployeeJPAEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private Long id;
    private String name;
    private String lastName;
    private Department department;

    public EmployeeJPAEntity toJpaEntity () {
        return new EmployeeJPAEntity(
                this.id,
                this.name,
                this.lastName,
                this.department != null ? this.department.toJpaEntity() : null
        );
    }

    public static Employee fromJpaEntity(EmployeeJPAEntity employeeJPA) {
        if (employeeJPA == null) {
            return null;
        }
        return new Employee(
                employeeJPA.getId(),
                employeeJPA.getName(),
                employeeJPA.getLastName(),
                Department.fromEntity(employeeJPA.getDepartment())
        );
    }

}
