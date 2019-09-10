package br.edu.ioxua.rarch.core.repository;

import br.edu.ioxua.rarch.core.entity.Entity;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface Repository<E extends Entity> {
    E save(E e);

    void removeById(Long id);
    void remove(E e);

    Collection<E> findAll();
    Optional<E> findById(Long id);
    Collection<E> findByExample(E example);
    Collection<?> runQuery(String query, Map<String, Object> params);
}
