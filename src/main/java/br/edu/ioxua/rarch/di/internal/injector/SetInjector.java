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
import java.util.HashSet;
import java.util.Set;

public class SetInjector extends AbstractInjector implements Injector<Set> {

    private Set<String> contextualGroups;
    private Set<String> contextualNames;

    public SetInjector() {
        this(new HashSet<>(), new HashSet<>());
    }

    SetInjector(Set<String> contextualGroups, Set<String> contextualNames) {
        this.contextualGroups = contextualGroups;
        this.contextualNames = contextualNames;
    }

    @Override
    public Set inject(InjectionContext context, InstantiationContext instantiationContext,
                      Field into, ImplementationSet implementations, ProviderSet providers)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, CircularDependencyException {

        Named named = into.getAnnotation(Named.class);
        Grouped grouped = into.getAnnotation(Grouped.class);

        Set<String> fieldNames = this.resolveNames(named);
        Set<String> fieldGroups = this.resolveGroups(grouped);

        Set<Object> fieldValue = new HashSet<>();
        for (Class clazz : implementations.getImplementations()) {
            boolean shouldInject = (null == named && null == grouped)
                    // If the field is named and this implementation's name is not included, ignore it
                    || (null != named && this.shouldInjectNamedImpl(clazz, fieldNames))
                            // If the field is group and this implementation's group is not included, ignore it
                    || (null != grouped && this.shouldInjectGroupedImpl(clazz, fieldGroups));

            if (!shouldInject) {
                continue;
            }

            Object instantiatedObject = instantiationContext.instantiateWithDependencies(clazz);
            context.injectInto(instantiatedObject);
            fieldValue.add(instantiatedObject);
        }

        for (Pair<Object, Method> pair : providers.getFactories()) {
            boolean shouldInject = (null == named && null == grouped)
                    // If the field is named and this implementation's name is not included, ignore it
                    || (null != named && this.shouldInjectNamedFactory(pair, fieldNames))
                    // If the field is group and this implementation's group is not included, ignore it
                    || (null != grouped && this.shouldInjectGroupedFactory(pair, fieldGroups));

            if (!shouldInject) {
                continue;
            }

            Object instantiatedObject = instantiationContext.instantiateWithDependencies(pair.getFirst(), pair.getSecond());
            context.injectInto(instantiatedObject);
            fieldValue.add(instantiatedObject);
        }

        return fieldValue;
    }

    private boolean shouldInjectGroupedImpl(Class clazz, Set<String> allowedGroups) {
        if (allowedGroups.size() == 0) return true;
        Group implGroup = (Group) clazz.getAnnotation(Group.class);
        return this.isNameIncluded(allowedGroups, implGroup)
                && (!this.contextualGroups.isEmpty() && this.isNameIncluded(this.contextualGroups, implGroup));
    }

    private boolean shouldInjectGroupedFactory(Pair<Object, Method> pair, Set<String> allowedGroups) {
        if (allowedGroups.size() == 0) return true;
        Group factoryGroup = pair.getSecond().getAnnotation(Group.class);
        return this.isNameIncluded(allowedGroups, factoryGroup)
                && (!this.contextualGroups.isEmpty() && this.isNameIncluded(this.contextualGroups, factoryGroup));
    }

    private boolean shouldInjectNamedFactory(Pair<Object, Method> pair, Set<String> allowedNames) {
        Named factoryNamed = pair.getSecond().getAnnotation(Named.class);
        return this.isNameIncluded(allowedNames, factoryNamed)
                && (this.contextualNames.isEmpty() || this.isNameIncluded(this.contextualNames, factoryNamed));
    }

    private boolean shouldInjectNamedImpl(Class clazz, Set<String> allowedNames) {
        Named implNamed = (Named) clazz.getAnnotation(Named.class);
        return this.isNameIncluded(allowedNames, implNamed)
                && (this.contextualNames.isEmpty() || this.isNameIncluded(this.contextualNames, implNamed));
    }

}
