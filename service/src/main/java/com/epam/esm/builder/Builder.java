package com.epam.esm.builder;

public interface Builder<T, B> {

    T build();

    B reset();
}
