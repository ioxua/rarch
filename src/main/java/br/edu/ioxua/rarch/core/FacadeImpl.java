package br.edu.ioxua.rarch.core;

import br.edu.ioxua.rarch.core.businessrule.BusinessRule;
import br.edu.ioxua.rarch.core.entity.Entity;
import br.edu.ioxua.rarch.core.operation.Operation;
import br.edu.ioxua.rarch.core.operation.Operations;
import br.edu.ioxua.rarch.core.repository.Repository;
import br.edu.ioxua.rarch.core.result.OperationResult;
import br.edu.ioxua.rarch.core.util.Util;
import br.edu.ioxua.rarch.di.annotation.Grouped;
import br.edu.ioxua.rarch.di.annotation.Injectable;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

// FIXME: DOES THIS INTERFACE HAVE TO INCLUDE A SINGLE METHOD TO EVERY OPERATION??
// SINCE THE OPERATION COMES ALL THE WAY HERE, THERE IS NO NEED TO
@Injectable
public class FacadeImpl implements Facade {

    @Inject
    private Map<String, Repository<? super Entity>> repositories;

    @Inject
    @Grouped({"SAVE", "FIND", "VIEW", "UPDATE", "LIST", "BY_DEPARTMENT"})
    private Map<String, Map<String, Set<BusinessRule<? super Entity>>>> businessRules;

    @Override
    public OperationResult<?> save(Entity entity) {
        return this.processBusinessRules(Operations.SAVE, entity, () ->
                repositories.get(entity.getClass().getName()).save(entity)
        );
    }

    @Override
    public OperationResult<?> update(Entity entity) {
        return this.processBusinessRules(Operations.UPDATE, entity, () ->
                repositories.get(entity.getClass().getName()).save(entity)
        );
    }

    @Override
    public OperationResult<?> find(Entity entity) {
        return this.processBusinessRules(Operations.FIND, entity, () ->
                repositories.get(
                        entity
                                .getClass()
                                .getName())
                        .findByExample(entity)
        );
    }

    @Override
    public OperationResult<?> view(Entity entity) {
        return this.processBusinessRules(Operations.VIEW, entity, () ->
                repositories.get(entity.getClass().getName()).findById(entity.getId())
        );
    }

    @Override
    public OperationResult<?> delete(Entity entity) {
        return this.processBusinessRules(Operations.DELETE, entity, () -> {
            repositories.get(entity.getClass().getName()).remove(entity);
            return true;
        });
    }

    @Override
    public OperationResult<?> custom(Operation operation, Entity entity) {
        return this.processBusinessRules(operation, entity);
    }

    @SuppressWarnings("unchecked")
    private OperationResult processBusinessRules(Operation operation, Entity entity, Supplier<Object> ifSuccessful) {
        OperationResult.OperationResultBuilder builder = OperationResult.builder();

        if (null != entity) {
            this.businessRules
                    .getOrDefault(operation.getName(), Collections.emptyMap())
                    .getOrDefault(entity.getClass().getName(), Collections.emptySet())
                    .parallelStream()
                    .forEachOrdered(rule -> rule.process(builder, entity));
        }

        OperationResult result = builder.build();

        if (null == ifSuccessful || Util.hasError(result.getMessages())) {
            return result;
        }

        return result.with(ifSuccessful.get());
    }

    private OperationResult processBusinessRules(Operation operation, Entity entity) {
        return processBusinessRules(operation, entity, null);
    }

}
