package br.edu.ioxua.rarch.di;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ImplementationSet {
    public static final ImplementationSet EMPTY = new ImplementationSet(null, null);

    private Class interfaceClass;
    private Set<Class> implementations;

    public ImplementationSet(Class clazz, Class impl) {
        this.interfaceClass = clazz;
        this.implementations = new HashSet<>();
        this.addImplementation(impl);
    }

    public int size() {
        return this.implementations.size();
    }

    public ImplementationSet addImplementation(Class clazz) {
        if (null != clazz) {
            this.implementations.add(clazz);
        }

        return this;
    }

    public boolean hasImplementations() {
        return !this.implementations.isEmpty();
    }
}
