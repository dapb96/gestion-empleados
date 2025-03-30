package com.prueba.gestionempleados.departaments.infrastructure.database.repositories;

import com.prueba.gestionempleados.departaments.infrastructure.database.enitites.DepartmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentJpaEntity, Long> {
}
