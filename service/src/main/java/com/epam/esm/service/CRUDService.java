package com.epam.esm.service;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public interface CRUDService<D extends RepresentationModel<D>> {

    List<D> findAll(Pageable pageable);

    List<D> findAll();

    D findById(Long id);

    void deleteById(Long id);

    D partialUpdate(D dto);

    D update(D dto);

    Long save(D dto);
}
