package br.edu.ioxua.rarch.core;

import br.edu.ioxua.rarch.core.entity.Entity;
import br.edu.ioxua.rarch.core.operation.Operation;
import br.edu.ioxua.rarch.core.result.OperationResult;

// TODO: RENAME THIS CLASS
public interface Facade {
    OperationResult<?> save(Entity entity);
    OperationResult<?> update(Entity entity);
    OperationResult<?> find(Entity entity);
    OperationResult<?> view(Entity entity);
    OperationResult<?> delete(Entity entity);
    OperationResult<?> custom(Operation operation, Entity entity);
}
