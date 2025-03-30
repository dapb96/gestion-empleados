package com.prueba.gestionempleados.employees.infrastructure.database.respositories;

import com.prueba.gestionempleados.employees.infrastructure.database.entities.EmployeeJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeJPAEntity, Long> {

    List<EmployeeJPAEntity> findBydepartmentId(Long departmentId);

    Optional<EmployeeJPAEntity> findBydepartmentIdAndId(Long departmentId, Long employeeId);

}
