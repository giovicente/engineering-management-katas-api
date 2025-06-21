package org.giovicente.engineering.management.katas.api.processor.tests.strategy.impl;

import org.giovicente.engineering.management.katas.api.core.processor.EngineeringManagementKatasApiProcessor;
import org.giovicente.engineering.management.katas.api.core.strategy.RandomKataStrategy;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Level;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.giovicente.engineering.management.katas.api.processor.strategy.impl.CategoryAndLevelRandomKataStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CategoryAndLevelRandomKataStrategyTest {

    private EngineeringManagementKatasApiProcessor processorMock;
    private RandomKataStrategy strategy;

    private Kata kata;

    @BeforeEach
    void setUp() {
        processorMock = Mockito.mock(EngineeringManagementKatasApiProcessor.class);
        strategy = new CategoryAndLevelRandomKataStrategy(processorMock);

        kata = Kata.builder()
                .id(UUID.randomUUID())
                .category(Category.TECHNICAL)
                .title("Daily Stand-up kata")
                .description("How to improve your team's daily meeting?")
                .level(Level.EASY)
                .build();
    }

    @Test
    void supports_shouldReturnTrueForNonNullAndNonBlankInput() {
        assertThat(strategy.supports("TECHNICAL", "EASY")).isTrue();
        assertThat(strategy.supports("  TECHNICAL ", "  EASY ")).isTrue();

        assertThat(strategy.supports("   TECHNICAL   ", "EASY")).isTrue();
        assertThat(strategy.supports("TECHNICAL", "   EASY   ")).isTrue();
    }

    @Test
    void supports_shouldReturnFalseForNullOrBlankInput() {
        assertThat(strategy.supports(null, null)).isFalse();

        assertThat(strategy.supports("", "")).isFalse();
        assertThat(strategy.supports("   ", "   ")).isFalse();

        assertThat(strategy.supports("", "   ")).isFalse();
        assertThat(strategy.supports("   ", "")).isFalse();

        assertThat(strategy.supports("", null)).isFalse();
        assertThat(strategy.supports("   ", null)).isFalse();

        assertThat(strategy.supports(null, "")).isFalse();
        assertThat(strategy.supports(null, "   ")).isFalse();

        assertThat(strategy.supports("TECHNICAL", null)).isFalse();
        assertThat(strategy.supports("   TECHNICAL   ", null)).isFalse();

        assertThat(strategy.supports("TECHNICAL", "")).isFalse();
        assertThat(strategy.supports("TECHNICAL", "   ")).isFalse();

        assertThat(strategy.supports("   TECHNICAL   ", "")).isFalse();
        assertThat(strategy.supports("   TECHNICAL   ", "   ")).isFalse();

        assertThat(strategy.supports(null, "EASY")).isFalse();
        assertThat(strategy.supports(null, "   EASY   ")).isFalse();

        assertThat(strategy.supports("", "EASY")).isFalse();
        assertThat(strategy.supports("   ", "EASY")).isFalse();

        assertThat(strategy.supports("", "   EASY   ")).isFalse();
        assertThat(strategy.supports("   ", "   EASY   ")).isFalse();
    }

    @Test
    void getKata_shouldDelegateToProcessorToGetRandomKataByCategoryAndLevel() {
        String category = "TECHNICAL";
        String level = "EASY";

        Mockito.when(
                processorMock.getRandomKataByCategoryAndLevel(
                        Category.valueOf(category),
                        Level.valueOf(level)
                    )
                )
                .thenReturn(kata);

        Kata result = strategy.getKata(category, level);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Daily Stand-up kata");

        Mockito.verify(processorMock).getRandomKataByCategoryAndLevel(
                Category.valueOf(category), Level.valueOf(level)
        );
    }
}
