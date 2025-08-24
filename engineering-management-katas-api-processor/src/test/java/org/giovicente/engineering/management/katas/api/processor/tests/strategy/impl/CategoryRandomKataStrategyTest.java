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

import java.util.Optional;
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
        assertThat(strategy.supports("TECHNICAL".describeConstable(), Optional.empty())).isTrue();
    }

    @Test
    void supports_shouldReturnFalseForNullOrBlankCategory() {
        assertThat(strategy.supports(Optional.empty(), Optional.empty())).isFalse();
    }

    @Test
    void getKata_shouldDelegateToProcessorToGetRandomKataByCategory() {
        Optional<String> category = Optional.of("TECHNICAL");
        System.out.println(Category.valueOf(category.orElse("Invalid Value")));
        Mockito.when(processorMock.getRandomKataByCategory(Category.valueOf(category.orElse("Invalid Value")))).thenReturn(kata);

        Kata result = strategy.getKata(category, Optional.empty());

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Daily Stand-up kata");

        Mockito.verify(processorMock).getRandomKataByCategory(Category.valueOf(category.orElse("Invalid Value")));
    }
}
