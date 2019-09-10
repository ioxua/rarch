package br.edu.ioxua.rarch.core.businessrule;

import br.edu.ioxua.rarch.core.entity.Entity;
import br.edu.ioxua.rarch.core.result.OperationResult;

public interface BusinessRule<E extends Entity> {
    void process(OperationResult.OperationResultBuilder builder, E entity);
}
