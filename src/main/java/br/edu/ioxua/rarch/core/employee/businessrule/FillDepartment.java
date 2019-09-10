package br.edu.ioxua.rarch.core.employee.businessrule;

import br.edu.ioxua.rarch.core.businessrule.BusinessRule;
import br.edu.ioxua.rarch.core.department.Department;
import br.edu.ioxua.rarch.core.department.DepartmentRepository;
import br.edu.ioxua.rarch.core.employee.Employee;
import br.edu.ioxua.rarch.core.result.Message;
import br.edu.ioxua.rarch.core.result.OperationResult;
import br.edu.ioxua.rarch.di.annotation.Group;
import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.annotation.Named;

import javax.inject.Inject;
import java.util.Optional;

@Group("SAVE")
@Named(clazz = Employee.class)
@Injectable
public class FillDepartment implements BusinessRule<Employee> {

    @Inject
    private DepartmentRepository departmentRepository;

    @Override
    public void process(OperationResult.OperationResultBuilder builder, Employee entity) {
        if (null == entity.getDepartment() || 1 > entity.getDepartment().getId()) {
            builder.message(
                    Message.error("When saving a new Employee, a department is required")
            );
            return;
        }

        Optional<Department> optionalDepartment = this.departmentRepository.findById(entity.getDepartment().getId());

        if (optionalDepartment.isEmpty()) {
            builder.message(
                    Message.error("The provided department id [" + entity.getDepartment().getId() + "] does not exist")
            );
            return;
        }

        entity.setDepartment(optionalDepartment.get());
    }
}
