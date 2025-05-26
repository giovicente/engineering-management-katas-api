package org.giovicente.engineering.management.katas.api.processor.tests.strategy.impl;

import org.giovicente.engineering.management.katas.api.core.EngineeringManagementKatasApiProcessor;
import org.giovicente.engineering.management.katas.api.core.strategy.RandomKataStrategy;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Language;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.giovicente.engineering.management.katas.api.processor.strategy.impl.DefaultRandomKataStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DefaultRandomKataStrategyTest {

    private EngineeringManagementKatasApiProcessor processorMock;
    private RandomKataStrategy strategy;

    private Kata kata;

    @BeforeEach
    void setUp() {
        processorMock = Mockito.mock(EngineeringManagementKatasApiProcessor.class);
        strategy = new DefaultRandomKataStrategy(processorMock);

        kata = Kata.builder()
                .id(UUID.randomUUID())
                .category(Category.TECHNICAL)
                .language(Language.EN_US)
                .title("Daily Stand-up kata")
                .description("How to improve your team's daily meeting?")
                .build();
    }

    @Test
    void supports_shouldReturnFalseForNonNullAndNonBlankCategory() {
        assertThat(strategy.supports("TECHNICAL")).isFalse();
        assertThat(strategy.supports("  BEHAVIORAL ")).isFalse();
    }

    @Test
    void supports_shouldReturnTrueForNullOrBlankCategory() {
        assertThat(strategy.supports(null)).isTrue();
        assertThat(strategy.supports("")).isTrue();
        assertThat(strategy.supports("   ")).isTrue();
    }

    @Test
    void getKata_shouldDelegateToProcessor() {
        Mockito.when(processorMock.getRandomKataByDefault()).thenReturn(kata);

        Kata result = strategy.getKata(null);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Daily Stand-up kata");

        Mockito.verify(processorMock).getRandomKataByDefault();
    }
}
