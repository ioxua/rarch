package br.edu.ioxua.rarch.di.internal;

import br.edu.ioxua.rarch.di.ImplementationSet;
import br.edu.ioxua.rarch.di.ProviderSet;
import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.annotation.Provides;
import br.edu.ioxua.rarch.di.exception.CircularDependencyException;
import br.edu.ioxua.rarch.di.internal.injector.FieldInjector;
import br.edu.ioxua.rarch.di.internal.injector.MapInjector;
import br.edu.ioxua.rarch.di.internal.injector.SetInjector;
import br.edu.ioxua.rarch.di.scanner.ClasspathScanner;

import javax.inject.Inject;
import java.lang.reflect.*;
import java.util.*;

/**
 * This does automatically a singleton. Maybe this is not the preferable way.
 * THis class also doesn't handle constructor injection
 * This also requires a default constructor to be available at all times.
 * This class only injects on Sets. No other collections
 */
public class InjectionContext {
    private InstantiationContext instantiationContext;
    private Class[] classes;
    private Map<Class, ImplementationSet> implementations;
    private Map<Class, ProviderSet> providers;

    public InjectionContext(String... packages) {
        this.instantiationContext = new InstantiationContext();
        this.implementations = new HashMap<>();
        this.providers = new HashMap<>();

        this.classes = ClasspathScanner.getClasses(packages);
        this.scanInjectableClasses(classes);

        try {
            this.scanProviderMethods(classes);
        } catch (Exception e) {
            System.err.println("Error when building factories");
            e.printStackTrace();
        }
    }

    public void injectInto(Object startingPoint) throws CircularDependencyException, IllegalAccessException, InvocationTargetException, InstantiationException {
        scanInjectableFields(startingPoint);
    }

    @SuppressWarnings("unchecked")
    void injectIntoField(Object instance, Field field)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, CircularDependencyException {
        Type type = field.getGenericType();
        // If the type is of a generic type (i.e. Map<?>) we need to know the type

        boolean isSearchingForType = type instanceof ParameterizedType;

        if (isSearchingForType) {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();

            while (isSearchingForType && null != parameterizedType) {
                // We get the last generic since Collection<THIS> and Map<?, THIS> are what matter.
                // Maybe another strategy design pattern here

                boolean typeHasAnyImpl = this.implementations.containsKey(parameterizedType.getRawType());
                boolean typeHasAnyProvider = this.providers.containsKey(parameterizedType.getRawType());

                isSearchingForType = !typeHasAnyImpl && !typeHasAnyProvider;

                if (isSearchingForType) {
                    int genericQuantity = parameterizedType.getActualTypeArguments().length;
                    type = parameterizedType.getActualTypeArguments()[genericQuantity-1];

                    if (type instanceof ParameterizedType) {
                        parameterizedType = (ParameterizedType) type;
                    } else {
                        parameterizedType = null;
                    }
                } else {
                    type = parameterizedType;
                }

            }
        }

        Class injectableType = null;
        if (type instanceof ParameterizedType) {
            injectableType = (Class) ((ParameterizedType) type).getRawType();
        } else if (type instanceof WildcardType) {
            Type[] lowerBounds = ((WildcardType) type).getLowerBounds();
            injectableType = (Class) lowerBounds[lowerBounds.length-1];
        } else {
            injectableType = (Class) type;
        }
        ImplementationSet impls = implementations.getOrDefault(injectableType, ImplementationSet.EMPTY);
        ProviderSet provs = providers.getOrDefault(injectableType, ProviderSet.EMPTY);

        if (!impls.hasImplementations() && !provs.hasProviders()) {
            // TODO: THROW NO AVAILABLE IMPL EXCEPTION
            throw new RuntimeException("NO AVAILABLE IMPL OR PROVIDER EXCEPTION FOR " + injectableType.getName());
        }

        field.setAccessible(true);
        Object fieldValue = null;

        // These if/else statements could be changed for a CoR pattern rs
        // The implementations ARE the same, but not the Maps/Sets
        if (Set.class.isAssignableFrom(field.getType())) {
            // Here, the interface instances are guaranteed to be the same, but not the sets.
            fieldValue = new SetInjector().inject(this, this.instantiationContext, field, impls, provs);
        } else if (Map.class.isAssignableFrom(field.getType())) {
            fieldValue = new MapInjector().inject(this, this.instantiationContext, field, impls, provs);
        } else {
            fieldValue = new FieldInjector().inject(this, this.instantiationContext, field, impls, provs);
        }

        field.set(instance, fieldValue);
    }

    void scanInjectableFields(Object object) throws IllegalAccessException, InvocationTargetException, InstantiationException, CircularDependencyException {
        List<Field> fields = this.accumulateDeclaredFields(object.getClass());

        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                injectIntoField(object, field);
            }
        }
    }

    void scanInjectableClasses(Class[] classes) {
        for (int i = 0; i < classes.length; i++) {
            if (classes[i].isAnnotationPresent(Injectable.class)) {
                listInterfacesAndAddToImplementationSet(classes[i]);
            }
        }
    }

    void scanProviderMethods(Class[] classes)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Class clazz : classes) {
            List<Method> methods = this.accumulateDeclaredMethods(clazz);
            for (Method method : methods) {
                if (method.isAnnotationPresent(Provides.class)) {
                    Class returnType = method.getReturnType();
                    Object providerClass = this.instantiationContext.instantiateWithDependencies(clazz);
                    if (providers.containsKey(returnType)) {
                        providers.get(returnType).addFactory(providerClass, method);
                    } else {
                        ProviderSet set = new ProviderSet(returnType, providerClass, method);
                        providers.put(returnType, set);
                    }
                }
            }
        }
    }

    void listInterfacesAndAddToImplementationSet(Class clazz) {
        List<Class> interfaces = this.accumulateImplementedInterfaces(clazz);
        if (implementations.containsKey(clazz)) {
            implementations.get(clazz).addImplementation(clazz);
        } else {
            ImplementationSet set = new ImplementationSet(clazz, clazz);
            implementations.put(clazz, set);
        }

        for (Class interfaze : interfaces) {
            if (implementations.containsKey(interfaze)) {
                implementations.get(interfaze).addImplementation(clazz);
            } else {
                ImplementationSet set = new ImplementationSet(interfaze, clazz);
                implementations.put(interfaze, set);
            }
        }
    }

    List<Class> accumulateImplementedInterfaces(Class clazz) {
        List<Class> interfaces = new ArrayList<>();
        Class currentClass = clazz;

        while (null != currentClass && !currentClass.equals(Object.class)) {
            Collections.addAll(interfaces, currentClass.getInterfaces());
            currentClass = currentClass.getSuperclass();
        }

        return interfaces;
    }

    List<Method> accumulateDeclaredMethods(Class clazz) {
        List<Method> methods = new ArrayList<>();
        Class currentClass = clazz;

        while (null != currentClass && !currentClass.equals(Object.class)) {
            Collections.addAll(methods, currentClass.getDeclaredMethods());
            currentClass = currentClass.getSuperclass();
        }

        return methods;
    }

    List<Field> accumulateDeclaredFields(Class clazz) {
        List<Field> fields = new ArrayList<>();
        Class currentClass = clazz;

        while (null != currentClass && !currentClass.equals(Object.class)) {
            Collections.addAll(fields, currentClass.getDeclaredFields());
            currentClass = currentClass.getSuperclass();
        }

        return fields;
    }
}