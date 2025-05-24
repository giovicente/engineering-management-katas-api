package org.giovicente.engineering.management.katas.api.adapter.tests.persistence;

import org.giovicente.engineering.management.katas.api.adapter.persistence.entity.KataEntity;
import org.giovicente.engineering.management.katas.api.adapter.persistence.repository.KataRepository;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = KataRepositoryTest.Config.class)
class KataRepositoryTest {

    @Configuration
    @EnableJpaRepositories(basePackages = "org.giovicente.engineering.management.katas.api.adapter.persistence.repository")
    @EntityScan(basePackages = "org.giovicente.engineering.management.katas.api.adapter.persistence.entity")
    static class Config { }

    @Autowired
    KataRepository repository;

    @Test
    void findRandomKata_shouldPersistAndReturnKata() {

        KataEntity entity = KataEntity.builder()
                .id(UUID.randomUUID())
                .category(Category.TECHNICAL)
                .language(Language.EN_US)
                .title("Daily Stand-up kata")
                .description("How to improve your team's daily meeting?")
                .build();

        repository.save(entity);
        KataEntity result = repository.findRandomKata();

        assertThat(result).isNotNull();
        assertThat(result.getCategory()).isEqualTo(entity.getCategory());
        assertThat(result.getLanguage()).isEqualTo(entity.getLanguage());
        assertThat(result.getTitle()).isEqualTo(entity.getTitle());
        assertThat(result.getDescription()).isEqualTo(entity.getDescription());
    }

    @Test
    void findRandomKata_shouldPersistAndReturnKataByCategory() {

        KataEntity entity = KataEntity.builder()
                .id(UUID.randomUUID())
                .category(Category.TECHNICAL)
                .language(Language.EN_US)
                .title("Daily Stand-up kata")
                .description("How to improve your team's daily meeting?")
                .build();

        repository.save(entity);
        KataEntity result = repository.findRandomKataByCategory(entity.getCategory().toString());

        assertThat(result).isNotNull();
        assertThat(result.getCategory()).isEqualTo(entity.getCategory());
        assertThat(result.getLanguage()).isEqualTo(entity.getLanguage());
        assertThat(result.getTitle()).isEqualTo(entity.getTitle());
        assertThat(result.getDescription()).isEqualTo(entity.getDescription());
    }
}
