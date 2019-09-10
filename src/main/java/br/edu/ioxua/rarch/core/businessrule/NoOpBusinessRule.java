package br.edu.ioxua.rarch.core.businessrule;

import br.edu.ioxua.rarch.core.entity.Entity;
import br.edu.ioxua.rarch.core.result.OperationResult;
import br.edu.ioxua.rarch.di.annotation.Group;
import br.edu.ioxua.rarch.di.annotation.Injectable;
import lombok.extern.java.Log;

import java.util.logging.Level;

@Log
@Group({"*"})
@Injectable
public class NoOpBusinessRule implements BusinessRule<Entity> {
    @Override
    public void process(OperationResult.OperationResultBuilder builder, Entity entity) {
        log.log(Level.INFO, "NoOp BusinessRule called");
    }
}
