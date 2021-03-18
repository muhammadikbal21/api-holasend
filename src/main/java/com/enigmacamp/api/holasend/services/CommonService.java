package com.enigmacamp.api.holasend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CommonService<T, ID> {

    T save(T entity);

    Page<T> findAll(T search, int page, int size, Sort.Direction direction);

}