package org.giovicente.engineering.management.katas.api.adapter.mapper;

import org.giovicente.engineering.management.katas.api.adapter.persistence.entity.KataEntity;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KataMapper {
    Kata toModel(KataEntity entity);
}
