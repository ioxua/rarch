package br.edu.ioxua.rarch.di.internal.injector;

import br.edu.ioxua.rarch.di.ImplementationSet;
import br.edu.ioxua.rarch.di.ProviderSet;
import br.edu.ioxua.rarch.di.annotation.Grouped;
import br.edu.ioxua.rarch.di.exception.CircularDependencyException;
import br.edu.ioxua.rarch.di.internal.InjectionContext;
import br.edu.ioxua.rarch.di.internal.InstantiationContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class MapInjector implements Injector<Map> {
    @Override
    public Map inject(InjectionContext context, InstantiationContext instantiationContext, Field into,
                      ImplementationSet implementations, ProviderSet providers)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, CircularDependencyException {

        Grouped grouped = into.getAnnotation(Grouped.class);
        if (null != grouped) {
            return new GroupedAndNamedMapInjector(grouped).inject(context, instantiationContext, into, implementations, providers);
        } else {
            return new SimpleMapInjector().inject(context, instantiationContext, into, implementations, providers);
        }
    }
}
