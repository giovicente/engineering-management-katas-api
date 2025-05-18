package org.giovicente.engineering.management.katas.api.controller.mapper;

import org.giovicente.engineering.management.katas.api.controller.dto.KataResponse;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KataDtoMapper {
    KataResponse toResponse(Kata kata);
}
