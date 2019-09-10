package br.edu.ioxua.rarch.di.internal.injector;

import br.edu.ioxua.rarch.di.ImplementationSet;
import br.edu.ioxua.rarch.di.ProviderSet;
import br.edu.ioxua.rarch.di.annotation.Named;
import br.edu.ioxua.rarch.di.exception.CircularDependencyException;
import br.edu.ioxua.rarch.di.internal.InjectionContext;
import br.edu.ioxua.rarch.di.internal.InstantiationContext;
import br.edu.ioxua.rarch.util.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FieldInjector implements Injector<Object> {

    @Override
    public Object inject(InjectionContext context, InstantiationContext instantiationContext, Field into,
                         ImplementationSet implementations, ProviderSet providers)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, CircularDependencyException {

        int options = implementations.size() + providers.size();

        if (options == 0) {
            throw new RuntimeException("NO INJECTABLE OPTIONS EXCEPTION");
        }

        if (options > 1) {
            if (!into.isAnnotationPresent(Named.class)) {
                // TODO: THROW TOO MANY QUALIFYING IMPLS EXCEPTION
                throw new RuntimeException("TOO MANY QUALIFYING IMPLS EXCEPTION");
            }
            throw new RuntimeException("TODO: IMPLEMENT NAMED SINGLE INJECTION");
        }

        Object fieldValue;
        if (implementations.hasImplementations()) {
            Class clazz = implementations.getImplementations().iterator().next();
            fieldValue = instantiationContext.instantiateWithDependencies(clazz);
        } else {
            Pair<Object, Method> pair = providers.getFactories().iterator().next();
            fieldValue = instantiationContext.instantiateWithDependencies(pair.getFirst(), pair.getSecond());
        }

        context.injectInto(fieldValue);
        return fieldValue;
    }

}
