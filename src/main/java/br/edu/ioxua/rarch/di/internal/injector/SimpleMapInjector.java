package br.edu.ioxua.rarch.di.internal.injector;

import br.edu.ioxua.rarch.di.ImplementationSet;
import br.edu.ioxua.rarch.di.ProviderSet;
import br.edu.ioxua.rarch.di.annotation.Group;
import br.edu.ioxua.rarch.di.annotation.Grouped;
import br.edu.ioxua.rarch.di.annotation.Named;
import br.edu.ioxua.rarch.di.exception.CircularDependencyException;
import br.edu.ioxua.rarch.di.internal.InjectionContext;
import br.edu.ioxua.rarch.di.internal.InstantiationContext;
import br.edu.ioxua.rarch.util.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class SimpleMapInjector extends AbstractInjector implements Injector<Map> {
    @Override
    public Map inject(InjectionContext context, InstantiationContext instantiationContext, Field into, ImplementationSet implementations, ProviderSet providers)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, CircularDependencyException {

        Map<String, Object> fieldValue = new HashMap<>();

        this.fillImplementations(context, instantiationContext, fieldValue, implementations);
        this.fillFactories(context, instantiationContext, fieldValue, providers);

        return fieldValue;
    }

    private void fillFactories(InjectionContext context, InstantiationContext instantiationContext,
                               Map<String, Object> map, ProviderSet providerSet)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, CircularDependencyException {

        for (Pair<Object, Method> pair: providerSet.getFactories()) {
            Object factoryInstance = pair.getFirst();
            Method factoryMethod = pair.getSecond();

            boolean shouldInject = factoryMethod.isAnnotationPresent(Named.class)
                    || factoryMethod.isAnnotationPresent(Group.class);

            if (!shouldInject) {
                continue;
            }

            Named named = factoryMethod.getAnnotation(Named.class);

            Object instantiatedObject = instantiationContext.instantiateWithDependencies(factoryInstance, factoryMethod);
            context.injectInto(instantiatedObject);

            for (String name : this.resolveNames(named)) {
                map.put(name, instantiatedObject);
            }
        }
    }

    private void fillImplementations(InjectionContext context, InstantiationContext instantiationContext,
                                     Map<String, Object> map, ImplementationSet implementations)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, CircularDependencyException {
        for (Class<?> clazz : implementations.getImplementations()) {
            boolean shouldInject = clazz.isAnnotationPresent(Named.class)
                    || clazz.isAnnotationPresent(Group.class);

            if (!shouldInject) {
                continue;
            }

            Object instantiatedObject = instantiationContext.instantiateWithDependencies(clazz);
            context.injectInto(instantiatedObject);

            Named named = clazz.getAnnotation(Named.class);

            for (String name : this.resolveNames(named)) {
                map.put(name, instantiatedObject);
            }
        }
    }
}
