package org.giovicente.engineering.management.katas.api.adapter.tests.mapper;

import org.giovicente.engineering.management.katas.api.adapter.mapper.KataMapper;
import org.giovicente.engineering.management.katas.api.adapter.persistence.entity.KataEntity;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Level;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class KataMapperTest {

    @Test
    void toModel_shouldMapKata() {
        KataEntity entity = KataEntity.builder()
                .id(UUID.randomUUID())
                .category(Category.TECHNICAL)
                .title("Daily Stand-up kata")
                .description("How to improve your team's daily meeting?")
                .level(Level.EASY)
                .build();

        final KataMapper mapper = Mappers.getMapper(KataMapper.class);
        Kata model = mapper.toModel(entity);

        assertThat(model).isNotNull();
        assertThat(model.getId()).isEqualTo(entity.getId());
        assertThat(model.getCategory()).isEqualTo(entity.getCategory());
        assertThat(model.getTitle()).isEqualTo(entity.getTitle());
        assertThat(model.getDescription()).isEqualTo(entity.getDescription());
        assertThat(model.getLevel()).isEqualTo(entity.getLevel());
    }
}
