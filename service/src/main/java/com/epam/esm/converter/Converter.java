package com.epam.esm.converter;

public interface Converter<Entity, Dto> {

    Entity convertToEntity(Dto dto);

    Dto convertToDto(Entity entity);
}
