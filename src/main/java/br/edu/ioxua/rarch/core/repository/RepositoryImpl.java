package br.edu.ioxua.rarch.core.repository;

import br.edu.ioxua.rarch.core.entity.Entity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
public class RepositoryImpl<E extends Entity> implements Repository<E> {

    private final EntityManagerFactory entityManagerFactory;
    private final Class<E> clazz;

    protected RepositoryImpl(Class<E> clazz) {
        this.clazz = clazz;
        this.entityManagerFactory =
                Persistence.createEntityManagerFactory("br.edu.ioxua.rarch");
    }

    protected EntityManager entityManager() throws IllegalStateException {
        return this.entityManagerFactory.createEntityManager();
    }

    @Override
    public E save(E entity) {
        return this.runSafely(manager -> {
            manager.persist(entity);
            return entity;
        });
    }

    @Override
    public Optional<E> findById(Long id) {
        return this.runSafely(
                manager -> Optional.ofNullable( manager.find(clazz, id) ));
    }

    @Override
    public void removeById(Long id) {
        this.runSafely(manager -> {
            Optional<E> optionalEntity = this.findById(id);
            optionalEntity.ifPresent(manager::remove);

            return null;
        });
    }

    @Override
    public void remove(E e) {
        this.removeById(e.getId());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<E> findAll() {
        return this.runSafely(
                manager ->
                        manager.createQuery("SELECT t FROM " + clazz.getSimpleName() + " t")
                                .getResultList()
                );
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Object> runQuery(String query, Map<String, Object> params) {
        return this.runSafely(manager -> {
            Query jpaQuery = manager.createQuery(query);

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                jpaQuery.setParameter(entry.getKey(), entry.getValue());
            }

            return jpaQuery.getResultList();
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<E> findByExample(E entity) {
        return this.runSafely(manager -> {
            Session session = manager.unwrap(Session.class);
            Example example = Example.create(entity).enableLike();

            return session.createCriteria(clazz).add(example).list();
        });
    }

    protected <R> R runSafely(Function<EntityManager, R> func) {
        EntityManager manager = null;

        try {
            manager = this.entityManager();
        } catch (IllegalStateException ex) {
            log.warn("EntityManagerFactory has been closed.");
            return null;
        }

        try {
            manager.getTransaction().begin();
            R result = func.apply(manager);
            manager.getTransaction().commit();

            return result;
        } catch (ClassCastException ex) {
            log.warn("Error executing cast", ex);
        } catch (RuntimeException ex) {
            log.warn("Error executing operation", ex);
        }

        if (manager.getTransaction().isActive()) {
            manager.getTransaction().rollback();
        }

        return null;
    }
}
