package org.giovicente.engineering.management.katas.api.processor.tests.strategy.impl;

import org.giovicente.engineering.management.katas.api.core.processor.EngineeringManagementKatasApiProcessor;
import org.giovicente.engineering.management.katas.api.core.strategy.RandomKataStrategy;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Level;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.giovicente.engineering.management.katas.api.processor.strategy.impl.LevelRandomKataStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LevelRandomKataStrategyTest {

    private EngineeringManagementKatasApiProcessor processorMock;
    private RandomKataStrategy strategy;

    private Kata kata;

    @BeforeEach
    void setUp() {
        processorMock = Mockito.mock(EngineeringManagementKatasApiProcessor.class);
        strategy = new LevelRandomKataStrategy(processorMock);

        kata = Kata.builder()
                .id(UUID.randomUUID())
                .category(Category.TECHNICAL)
                .title("Daily Stand-up kata")
                .description("How to improve your team's daily meeting?")
                .level(Level.EASY)
                .build();
    }

    @Test
    void supports_shouldReturnTrueForNonNullAndNonBlankLevel() {
        assertThat(strategy.supports(Optional.empty(), "EASY".describeConstable())).isTrue();
        assertThat(strategy.supports(Optional.empty(), "  HARD  ".describeConstable())).isTrue();
    }

    @Test
    void supports_shouldReturnFalseForNullOrBlankLevel() {
        assertThat(strategy.supports(Optional.empty(), Optional.empty())).isFalse();
    }

    @Test
    void getKata_shouldDelegateToProcessorToGetRandomKataByLevel() {
        Optional<String> level = Optional.of("EASY");

        Mockito.when(processorMock.getRandomKataByLevel(Level.valueOf(level.orElse("Invalid Value")))).thenReturn(kata);
        Kata result = strategy.getKata(Optional.empty(), level);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Daily Stand-up kata");

        Mockito.verify(processorMock).getRandomKataByLevel(Level.valueOf(level.orElse("Invalid Value")));
    }
}
