package br.edu.ioxua.rarch.di.internal.injector;

import br.edu.ioxua.rarch.di.ImplementationSet;
import br.edu.ioxua.rarch.di.ProviderSet;
import br.edu.ioxua.rarch.di.annotation.Grouped;
import br.edu.ioxua.rarch.di.annotation.Named;
import br.edu.ioxua.rarch.di.exception.CircularDependencyException;
import br.edu.ioxua.rarch.di.internal.InjectionContext;
import br.edu.ioxua.rarch.di.internal.InstantiationContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class GroupedAndNamedMapInjector extends AbstractInjector implements Injector<Map> {

    private final Grouped grouped;

    GroupedAndNamedMapInjector(Grouped grouped) {
        this.grouped = grouped;
    }

    @Override
    public Map inject(InjectionContext context, InstantiationContext instantiationContext, Field into,
                      ImplementationSet implementations, ProviderSet providers)
            throws IllegalAccessException, InvocationTargetException, InstantiationException,
                   CircularDependencyException {

        Map<String, Map<String, Set>> fieldValue = new HashMap<>();

        Set<String> groups = this.resolveGroups(grouped);
        Set<String> names = resolveImplementationNames(implementations);

        for (String group : groups) {
            Map<String, Set> implsMappedByName = new HashMap<>();

            for (String name : names) {
                SetInjector setInjector = new SetInjector(Set.of(group), Set.of(name));
                Set set = setInjector.inject(context, instantiationContext, into, implementations, providers);
                implsMappedByName.put(name, set);
            }

            fieldValue.put(group, implsMappedByName);
        }

        return fieldValue;
    }

    private Set<String> resolveImplementationNames(ImplementationSet implementationSet) {
        Set<String> set = new HashSet<>();
        for (Class clazz : implementationSet.getImplementations()) {
            if (clazz.isAnnotationPresent(Named.class)) {
                Named named = (Named) clazz.getAnnotation(Named.class);
                set.addAll(Arrays.asList(named.value()));

                if (!Void.class.isAssignableFrom(named.clazz())) {
                    set.add(named.clazz().getName());
                }
            }
        }
        return set;
    }
}
