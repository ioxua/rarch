package br.edu.ioxua.rarch.di.internal.injector;

import br.edu.ioxua.rarch.di.annotation.Group;
import br.edu.ioxua.rarch.di.annotation.Grouped;
import br.edu.ioxua.rarch.di.annotation.Named;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractInjector {

    protected Set<String> resolveNames(Named named) {
        Set<String> names = new HashSet<>();

        if (null == named) return names;

        if (Void.class.isAssignableFrom(named.clazz()) && named.value().length == 0) {
            // TODO: THROW NO STRING NAME OR CLASS EXCEPTION
            throw new RuntimeException("NO STRING NAME OR CLASS EXCEPTION");
        }

        if (!Void.class.isAssignableFrom(named.clazz())) {
            String name = named.clazz().getName();
            names.add(name);
        }

        for (String name : named.value()) {
            if (!name.trim().isEmpty()) {
                names.add(name);
            }
        }

        return names;
    }

    protected Set<String> resolveGroups(Grouped grouped) {
        Set<String> groups = new HashSet<>();

        if (null == grouped) return groups;

        boolean allGroups = grouped.classes().length == 0 && grouped.value().length == 0;

        if (allGroups) {
            groups.add("*");
            return groups;
        }

        for (Class clazz : grouped.classes()) {
            groups.add(clazz.getName());
        }

        for (String name : grouped.value()) {
            if (!name.trim().isEmpty()) {
                groups.add(name);
            }
        }

        return groups;
    }

    protected Set<String> resolveGroups(Group group) {
        Set<String> groups = new HashSet<>();

        if (null == group) return groups;

        if (Void.class.isAssignableFrom(group.clazz()) && group.value().length == 0) {
            // TODO: THROW NO STRING NAME OR CLASS EXCEPTION
            throw new RuntimeException("NO STRING NAME OR CLASS EXCEPTION");
        }

        if (!Void.class.isAssignableFrom(group.clazz())) {
            String name = group.clazz().getName();
            groups.add(name);
        }

        for (String name : group.value()) {
            if (!name.trim().isEmpty()) {
                groups.add(name);
            }
        }

        return groups;
    }

    protected boolean isNameIncluded(Set<String> names, Named named) {
        if (names.contains("*")) return true;
        Set<String> implNames = this.resolveNames(named);
        boolean isNameIncluded = false;

        for (String implName : implNames) {
            if (names.contains(implName)) {
                isNameIncluded = true;
                break;
            }
        }

        return isNameIncluded;
    }

    protected boolean isNameIncluded(Set<String> groups, Group group) {
        if (groups.contains("*")) return true;
        Set<String> implGroups = this.resolveGroups(group);
        boolean isNameIncluded = false;

        for (String implGroup : implGroups) {
            if (groups.contains(implGroup)) {
                isNameIncluded = true;
                break;
            }
        }

        return isNameIncluded;
    }

}
