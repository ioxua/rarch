package br.edu.ioxua.rarch.web.command;

import br.edu.ioxua.rarch.core.entity.Entity;
import br.edu.ioxua.rarch.core.operation.Operation;
import br.edu.ioxua.rarch.core.result.OperationResult;
import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.annotation.Named;

@Injectable
@Named("FIND")
public class FindCommand extends AbstractCommand {
    @Override
    public <E extends Entity> OperationResult<?> execute(Operation operation, E entity) {
        return this.facade.find(entity);
    }
}
