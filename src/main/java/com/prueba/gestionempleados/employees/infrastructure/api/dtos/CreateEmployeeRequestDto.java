package com.prueba.gestionempleados.employees.infrastructure.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRequestDto {

    private String name;
    private String lastName;
    private Long departamentoId;

}
