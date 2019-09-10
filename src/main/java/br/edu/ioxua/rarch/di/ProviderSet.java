package br.edu.ioxua.rarch.di;

import br.edu.ioxua.rarch.util.Pair;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@Getter
public class ProviderSet {
    public static final ProviderSet EMPTY = new ProviderSet(null, null, null);

    private Class interfaceClass;
    private Set<Pair<Object, Method>> factories;

    public ProviderSet(Class clazz, Object obj, Method factory) {
        this.interfaceClass = clazz;
        this.factories = new HashSet<>();
        this.addFactory(obj, factory);
    }

    public int size() {
        return this.factories.size();
    }

    @SuppressWarnings("unchecked")
    public ProviderSet addFactory(Object obj, Method method) {
        if (null != method) {
            this.factories.add(new Pair(obj, method));
        }

        return this;
    }

    public boolean hasProviders() {
        return !this.factories.isEmpty();
    }
}
