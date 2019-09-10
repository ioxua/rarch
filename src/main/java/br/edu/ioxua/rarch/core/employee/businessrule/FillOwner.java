package br.edu.ioxua.rarch.core.employee.businessrule;

import br.edu.ioxua.rarch.core.businessrule.BusinessRule;
import br.edu.ioxua.rarch.core.employee.Employee;
import br.edu.ioxua.rarch.core.employee.EmployeeRepository;
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
public class FillOwner implements BusinessRule<Employee> {

    @Inject
    private EmployeeRepository employeeRepository;

    @Override
    public void process(OperationResult.OperationResultBuilder builder, Employee entity) {
        if (null == entity.getOwner() || 1 > entity.getOwner().getId()) {
            builder.message(
                    Message.error("When saving a new Employee, a owner is required")
            );
            return;
        }

        Optional<Employee> optionalEmployee = this.employeeRepository.findById(entity.getOwner().getId());

        if (optionalEmployee.isEmpty()) {
            builder.message(
                    Message.error("The provided owner employee id [" + entity.getOwner().getId() + "] does not exist")
            );
            return;
        }

        entity.setOwner(optionalEmployee.get());
    }
}
