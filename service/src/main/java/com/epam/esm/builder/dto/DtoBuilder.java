package com.epam.esm.builder.dto;

public interface DtoBuilder<Dto extends com.epam.esm.dto.Dto, Builder extends DtoBuilder>
        extends com.epam.esm.builder.Builder<Dto, Builder> {

    Builder setId(Long id);
}
