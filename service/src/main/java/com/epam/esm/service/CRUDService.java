package com.epam.esm.service;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public interface CRUDService<D extends RepresentationModel<D>> {

    List<D> findAll(Pageable pageable);

    D findById(Long id);

    boolean deleteById(Long id);

    D update(D dto);

    Long save(D dto);
}
