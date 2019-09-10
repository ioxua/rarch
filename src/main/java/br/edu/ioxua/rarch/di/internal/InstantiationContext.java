package br.edu.ioxua.rarch.di.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class InstantiationContext {
    private final Map<Class, Object> instances = new HashMap<>();

    public Object instantiateWithDependencies(Class<?> clazz)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (this.instances.containsKey(clazz)) {
            return this.instances.get(clazz);
        }

        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (int i = 0; i < constructors.length; i++) {
            if (0 == constructors[i].getParameterCount()) {
                constructors[i].setAccessible(true);

                Object instance = constructors[i].newInstance();
                this.instances.put(clazz, instance);
                return instance;
            }
        }

        // TODO: THROW NO DEFAULT CONSTRUCTOR EXCEPTION
        throw new RuntimeException();
    }

    public Object instantiateWithDependencies(Object object, Method factory)
            throws IllegalAccessException, InvocationTargetException {

        Class clazz = factory.getReturnType();

        if (this.instances.containsKey(clazz)) {
            return this.instances.get(clazz);
        }

        if (factory.getParameterCount() > 0 || factory.isVarArgs()) {
            throw new RuntimeException("FACTORY METHODS MUST TAKE NO ARGUMENTS");
        }

        Object instance = factory.invoke(object);
        this.instances.put(clazz, instance);
        return instance;
    }
}
