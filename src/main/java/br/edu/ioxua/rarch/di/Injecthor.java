package br.edu.ioxua.rarch.di;

import br.edu.ioxua.rarch.di.internal.InjectionContext;

public abstract class Injecthor {
    public static void autowire(Object startingPoint, String... packages) {
        try {
            new InjectionContext(packages).injectInto(startingPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
