package br.edu.ioxua.rarch.core.employee.businessrule;

import br.edu.ioxua.rarch.core.businessrule.BusinessRule;
import br.edu.ioxua.rarch.core.department.DepartmentRepository;
import br.edu.ioxua.rarch.core.employee.Employee;
import br.edu.ioxua.rarch.core.result.OperationResult;
import br.edu.ioxua.rarch.core.department.dto.EmployeeQuantityByDepartmentDTO;
import br.edu.ioxua.rarch.di.annotation.Group;
import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.annotation.Named;

import javax.inject.Inject;
import java.util.List;

@Group("BY_DEPARTMENT")
@Named(clazz = Employee.class)
@Injectable
public class EmployeeByDepartment implements BusinessRule<Employee> {

    @Inject
    private DepartmentRepository departmentRepository;

    @Override
    public void process(OperationResult.OperationResultBuilder builder, Employee entity) {
        List<EmployeeQuantityByDepartmentDTO> employeeQuantityByDepartment = departmentRepository.findEnabledGroupByDepartment();
        builder.result(employeeQuantityByDepartment);
    }
}
