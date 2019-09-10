package br.edu.ioxua.rarch.core.department.dto;

import br.edu.ioxua.rarch.core.department.Department;
import lombok.Value;

@Value
public class EmployeeQuantityByDepartmentDTO {
    private final String departmentName;
    private final Integer employeeQuantity;

    public EmployeeQuantityByDepartmentDTO(Department department) {
        this.departmentName = department.getName();
        this.employeeQuantity = department.getEmployed().size();
    }
}
