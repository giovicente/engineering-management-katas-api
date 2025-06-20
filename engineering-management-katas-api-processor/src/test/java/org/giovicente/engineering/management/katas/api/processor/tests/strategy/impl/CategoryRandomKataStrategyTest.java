package org.giovicente.engineering.management.katas.api.processor.tests.strategy.impl;

import org.giovicente.engineering.management.katas.api.core.processor.EngineeringManagementKatasApiProcessor;
import org.giovicente.engineering.management.katas.api.core.strategy.RandomKataStrategy;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Level;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.giovicente.engineering.management.katas.api.processor.strategy.impl.CategoryRandomKataStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CategoryRandomKataStrategyTest {

    private EngineeringManagementKatasApiProcessor processorMock;
    private RandomKataStrategy strategy;

    private Kata kata;

    @BeforeEach
    void setUp() {
        processorMock = Mockito.mock(EngineeringManagementKatasApiProcessor.class);
        strategy = new CategoryRandomKataStrategy(processorMock);

        kata = Kata.builder()
                .id(UUID.randomUUID())
                .category(Category.TECHNICAL)
                .title("Daily Stand-up kata")
                .description("How to improve your team's daily meeting?")
                .level(Level.EASY)
                .build();
    }

    @Test
    void supports_shouldReturnTrueForNonNullAndNonBlankCategory() {
        assertThat(strategy.supports("TECHNICAL", null)).isTrue();
        assertThat(strategy.supports("  BEHAVIORAL ", null)).isTrue();
    }

    @Test
    void supports_shouldReturnFalseForNullOrBlankCategory() {
        assertThat(strategy.supports(null, null)).isFalse();
        assertThat(strategy.supports("", null)).isFalse();
        assertThat(strategy.supports("   ", null)).isFalse();
    }

    @Test
    void getKata_shouldDelegateToProcessorToGetRandomKataByCategory() {
        String category = "TECHNICAL";
        Mockito.when(processorMock.getRandomKataByCategory(Category.valueOf(category))).thenReturn(kata);

        Kata result = strategy.getKata(category, null);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Daily Stand-up kata");

        Mockito.verify(processorMock).getRandomKataByCategory(Category.valueOf(category));
    }
}
