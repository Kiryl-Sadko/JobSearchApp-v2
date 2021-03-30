package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

public abstract class Dto<T extends RepresentationModel<T>> extends RepresentationModel<T> {
}
