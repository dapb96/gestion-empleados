package com.prueba.gestionempleados.departaments.domain;

import com.prueba.gestionempleados.departaments.infrastructure.database.enitites.DepartmentJpaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    private Long id;
    private String name;

    public DepartmentJpaEntity toJpaEntity() {
        return new DepartmentJpaEntity(this.id, this.name);
    }

    public static Department fromEntity(DepartmentJpaEntity departmentJpa) {
        if(departmentJpa == null) {
            return null;
        }
        return new Department(departmentJpa.getId(), departmentJpa.getName());
    }

}
