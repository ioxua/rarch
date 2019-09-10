package br.edu.ioxua.rarch.di.internal.injector;

import br.edu.ioxua.rarch.di.ImplementationSet;
import br.edu.ioxua.rarch.di.ProviderSet;
import br.edu.ioxua.rarch.di.exception.CircularDependencyException;
import br.edu.ioxua.rarch.di.internal.InjectionContext;
import br.edu.ioxua.rarch.di.internal.InstantiationContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface Injector<R> {
    R inject(InjectionContext context, InstantiationContext instantiationContext, Field into,
             ImplementationSet implementations, ProviderSet providers)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, CircularDependencyException;
}
