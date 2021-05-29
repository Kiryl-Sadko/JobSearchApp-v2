package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public abstract class Dto<T extends RepresentationModel<T>> extends RepresentationModel<T> implements Serializable {
}
