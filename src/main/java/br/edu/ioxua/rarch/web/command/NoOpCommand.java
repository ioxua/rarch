package br.edu.ioxua.rarch.web.command;

import br.edu.ioxua.rarch.core.entity.Entity;
import br.edu.ioxua.rarch.core.operation.Operation;
import br.edu.ioxua.rarch.core.result.OperationResult;
import lombok.extern.java.Log;

import java.util.logging.Level;

@Log
public class NoOpCommand implements OperationCommand {

    public static final OperationCommand INSTANCE = new NoOpCommand();

    private NoOpCommand() {}

    @Override
    public <E extends Entity> OperationResult<?> execute(Operation operation, E entity) {
        log.log(Level.INFO, "NoOp Command called with operation " + operation.getName());
        return OperationResult.of(null);
    }
}
