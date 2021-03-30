package com.epam.esm.builder.entity;

public interface EntityBuilder<Entity, Builder extends EntityBuilder>
        extends com.epam.esm.builder.Builder<Entity, Builder> {

    Builder setId(Long id);
}
