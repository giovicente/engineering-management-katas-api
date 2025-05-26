package org.giovicente.engineering.management.katas.api.processor.tests.strategy.impl;

import org.giovicente.engineering.management.katas.api.core.EngineeringManagementKatasApiProcessor;
import org.giovicente.engineering.management.katas.api.core.strategy.RandomKataStrategy;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Language;
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
                .language(Language.EN_US)
                .title("Daily Stand-up kata")
                .description("How to improve your team's daily meeting?")
                .build();
    }

    @Test
    void supports_shouldReturnTrueForNonNullAndNonBlankCategory() {
        assertThat(strategy.supports("TECHNICAL")).isTrue();
        assertThat(strategy.supports("  BEHAVIORAL ")).isTrue();
    }

    @Test
    void supports_shouldReturnFalseForNullOrBlankCategory() {
        assertThat(strategy.supports(null)).isFalse();
        assertThat(strategy.supports("")).isFalse();
        assertThat(strategy.supports("   ")).isFalse();
    }

    @Test
    void getKata_shouldDelegateToProcessor() {
        String category = "TECHNICAL";
        Mockito.when(processorMock.getRandomKataByCategory(Category.valueOf(category))).thenReturn(kata);

        Kata result = strategy.getKata(category);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Daily Stand-up kata");

        Mockito.verify(processorMock).getRandomKataByCategory(Category.valueOf(category));
    }
}
