package com.ktk.duka.mygoal.service.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Optional;

public abstract class CrudService<F, E extends CrudEntity<E, L>, L extends Serializable> {

    public E save(E entity) {
        return getRepository().save(entity);
    }

    public Iterable<E> saveAll(Iterable<E> entities) {
        return getRepository().saveAll(entities);
    }

    public void delete(E entity) {
        getRepository().delete(entity);
    }

    public void deleteAll(Iterable<E> entities) {
        getRepository().deleteAll(entities);
    }

    public void deleteAll() {
        getRepository().deleteAll();
    }

    public boolean existsById(L id) {
        return getRepository().existsById(id);
    }

    public long count() {
        return getRepository().count();
    }

    public long countByQuery(F filter) {
        return 0L;
    }

    public void getById(L id) {
        getRepository().getById(id);
    }

    public Optional<E> findById(L id) {
        return getRepository().findById(id);
    }

    public Iterable<E> findAll() {
        return getRepository().findAll();
    }

    public Iterable<E> findAllByIds(Iterable<L> ids) {
        return getRepository().findAllById(ids);
    }

    public Page<E> fetch(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    public Page<E> fetchByQuery(F filter, Pageable pageable) {
        return Page.empty();
    }

    public abstract JpaRepository<E, L> getRepository();

    public abstract Class<E> getEntityClass();

    public abstract Class<F> getFilterClass();

    public abstract E createEntity();

    public abstract F createFilter();
}
