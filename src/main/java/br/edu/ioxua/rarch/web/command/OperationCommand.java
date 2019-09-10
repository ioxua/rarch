package br.edu.ioxua.rarch.web.command;

import br.edu.ioxua.rarch.core.entity.Entity;
import br.edu.ioxua.rarch.core.operation.Operation;
import br.edu.ioxua.rarch.core.result.OperationResult;

/**
 * Implements a command on the Facade
 */
public interface OperationCommand {
    <E extends Entity> OperationResult<?> execute(Operation operation, E entity);
}
