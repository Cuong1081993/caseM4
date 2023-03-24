package com.example.service;

import java.util.List;
import java.util.Optional;

public interface IGeneralService<T> {
    List<T> findAll();

    Optional<T> findById(Long id);


    T save(T t);

    void deleted(T t);

    void deletedById(Long id);

    boolean existById(Long id);
}
