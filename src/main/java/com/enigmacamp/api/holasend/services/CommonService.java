package com.enigmacamp.api.holasend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CommonService<T, ID> {

    public T save(T entity);

    public Page<T> findAll(T search, int page, int size, Sort.Direction direction);

}