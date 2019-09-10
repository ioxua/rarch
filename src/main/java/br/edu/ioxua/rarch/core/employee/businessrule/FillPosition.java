package br.edu.ioxua.rarch.core.employee.businessrule;

import br.edu.ioxua.rarch.core.businessrule.BusinessRule;
import br.edu.ioxua.rarch.core.employee.Employee;
import br.edu.ioxua.rarch.core.position.Position;
import br.edu.ioxua.rarch.core.position.PositionRepository;
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
public class FillPosition implements BusinessRule<Employee> {

    @Inject
    private PositionRepository positionRepository;

    @Override
    public void process(OperationResult.OperationResultBuilder builder, Employee entity) {
        if (null == entity.getPosition() || 1 > entity.getPosition().getId()) {
            builder.message(
                    Message.error("When saving a new Employee, a position is required")
            );
            return;
        }

        Optional<Position> optionalPosition = this.positionRepository.findById(entity.getPosition().getId());

        if (optionalPosition.isEmpty()) {
            builder.message(
                    Message.error("The provided position id [" + entity.getPosition().getId() + "] does not exist")
            );
            return;
        }

        entity.setPosition(optionalPosition.get());
    }
}
