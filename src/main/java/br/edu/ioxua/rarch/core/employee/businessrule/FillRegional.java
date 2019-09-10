package br.edu.ioxua.rarch.core.employee.businessrule;

import br.edu.ioxua.rarch.core.businessrule.BusinessRule;
import br.edu.ioxua.rarch.core.employee.Employee;
import br.edu.ioxua.rarch.core.regional.Regional;
import br.edu.ioxua.rarch.core.regional.RegionalRepository;
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
public class FillRegional implements BusinessRule<Employee> {

    @Inject
    private RegionalRepository regionalRepository;

    @Override
    public void process(OperationResult.OperationResultBuilder builder, Employee entity) {
        if (null == entity.getRegional() || 1 > entity.getRegional().getId()) {
            builder.message(
                    Message.error("When saving a new Employee, a regional is required")
            );
            return;
        }

        Optional<Regional> optionalRegional = this.regionalRepository.findById(entity.getRegional().getId());

        if (optionalRegional.isEmpty()) {
            builder.message(
                    Message.error("The provided regional id [" + entity.getRegional().getId() + "] does not exist")
            );
            return;
        }

        entity.setRegional(optionalRegional.get());
    }
}
