package org.giovicente.engineering.management.katas.api.processor.tests.strategy.handler;

import org.giovicente.engineering.management.katas.api.core.strategy.RandomKataStrategy;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Level;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.giovicente.engineering.management.katas.api.processor.strategy.handler.RandomKataHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RandomKataHandlerTest {

    private RandomKataHandler handler;
    private RandomKataStrategy categoryStrategyMock;
    private RandomKataStrategy defaultStrategyMock;
    private RandomKataStrategy categoryAndLevelStrategyMock;

    private Kata kata;

    @BeforeEach
    void setUp() {
        categoryStrategyMock = Mockito.mock(RandomKataStrategy.class);
        defaultStrategyMock = Mockito.mock(RandomKataStrategy.class);
        categoryAndLevelStrategyMock = Mockito.mock(RandomKataStrategy.class);

        List<RandomKataStrategy> strategies = Arrays.asList(categoryStrategyMock, categoryAndLevelStrategyMock, defaultStrategyMock);
        handler = new RandomKataHandler(strategies);

        kata = Kata.builder()
                .id(UUID.randomUUID())
                .category(Category.TECHNICAL)
                .title("Daily Stand-up kata")
                .description("How to improve your team's daily meeting?")
                .level(Level.EASY)
                .build();
    }

    @Test
    void handle_shouldUseCategoryStrategy() {
        Mockito.when(categoryStrategyMock.supports(Category.TECHNICAL.name(), null)).thenReturn(true);
        Mockito.when(categoryStrategyMock.getKata(Category.TECHNICAL.name(), null)).thenReturn(kata);

        Kata result = handler.handle(Category.TECHNICAL.name(), null);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Daily Stand-up kata");

        Mockito.verify(categoryStrategyMock).getKata(Category.TECHNICAL.name(), null);

        Mockito.verify(defaultStrategyMock, Mockito.never()).getKata(Mockito.any(), Mockito.any());
        Mockito.verify(categoryAndLevelStrategyMock, Mockito.never()).getKata(Mockito.any(), Mockito.any());
    }

    @Test
    void handle_shouldUseDefaultStrategy() {
        Mockito.when(defaultStrategyMock.supports(null, null)).thenReturn(true);
        Mockito.when(defaultStrategyMock.getKata(null, null)).thenReturn(kata);

        Kata result = handler.handle(null, null);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Daily Stand-up kata");

        Mockito.verify(defaultStrategyMock).getKata(null, null);

        Mockito.verify(categoryStrategyMock, Mockito.never()).getKata(Mockito.any(), Mockito.any());
        Mockito.verify(categoryAndLevelStrategyMock, Mockito.never()).getKata(Mockito.any(), Mockito.any());
    }

    @Test
    void handle_shouldUseCategoryAndLevelStrategy() {
        Mockito.when(categoryAndLevelStrategyMock.supports(Category.TECHNICAL.name(), Level.EASY.name())).thenReturn(true);
        Mockito.when(categoryAndLevelStrategyMock.getKata(Category.TECHNICAL.name(), Level.EASY.name())).thenReturn(kata);

        Kata result = handler.handle(Category.TECHNICAL.name(), Level.EASY.name());

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Daily Stand-up kata");

        Mockito.verify(categoryAndLevelStrategyMock).getKata(Category.TECHNICAL.name(), Level.EASY.name());

        Mockito.verify(categoryStrategyMock, Mockito.never()).getKata(Mockito.any(), Mockito.any());
        Mockito.verify(defaultStrategyMock, Mockito.never()).getKata(Mockito.any(), Mockito.any());
    }

    @Test
    void handle_shouldThrowExceptionWhenNoStrategySupportsByCategory() {
        String invalidCategory = "SOCCER";

        Mockito.when(categoryStrategyMock.supports(invalidCategory, null)).thenReturn(false);
        Mockito.when(categoryAndLevelStrategyMock.supports(invalidCategory, null)).thenReturn(false);
        Mockito.when(defaultStrategyMock.supports(invalidCategory, null)).thenReturn(false);

        assertThatThrownBy(() -> handler.handle(invalidCategory, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No strategy found for provided parameters");

        Mockito.verify(categoryStrategyMock).supports(invalidCategory, null);
        Mockito.verify(categoryAndLevelStrategyMock).supports(invalidCategory, null);
        Mockito.verify(defaultStrategyMock).supports(invalidCategory, null);

        Mockito.verifyNoMoreInteractions(categoryStrategyMock, categoryAndLevelStrategyMock, defaultStrategyMock);
    }

    @Test
    void handle_shouldThrowExceptionWhenNoStrategySupportsByLevel() {
        String invalidLevel = "NIGHTMARE";

        Mockito.when(categoryStrategyMock.supports(null, invalidLevel)).thenReturn(false);
        Mockito.when(categoryAndLevelStrategyMock.supports(null, invalidLevel)).thenReturn(false);
        Mockito.when(defaultStrategyMock.supports(null, invalidLevel)).thenReturn(false);

        assertThatThrownBy(() -> handler.handle(null, invalidLevel))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No strategy found for provided parameters");

        Mockito.verify(categoryStrategyMock).supports(null, invalidLevel);
        Mockito.verify(categoryAndLevelStrategyMock).supports(null, invalidLevel);
        Mockito.verify(defaultStrategyMock).supports(null, invalidLevel);

        Mockito.verifyNoMoreInteractions(categoryStrategyMock, categoryAndLevelStrategyMock, defaultStrategyMock);
    }

    @Test
    void handle_shouldThrowExceptionWhenNoStrategySupportsByCategoryAndLevel() {
        String invalidCategory = "SOCCER";
        String invalidLevel = "NIGHTMARE";

        Mockito.when(categoryStrategyMock.supports(invalidCategory, invalidLevel)).thenReturn(false);
        Mockito.when(categoryAndLevelStrategyMock.supports(invalidCategory, invalidLevel)).thenReturn(false);
        Mockito.when(defaultStrategyMock.supports(invalidCategory, invalidLevel)).thenReturn(false);

        assertThatThrownBy(() -> handler.handle(invalidCategory, invalidLevel))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No strategy found for provided parameters");

        Mockito.verify(categoryStrategyMock).supports(invalidCategory, invalidLevel);
        Mockito.verify(categoryAndLevelStrategyMock).supports(invalidCategory, invalidLevel);
        Mockito.verify(defaultStrategyMock).supports(invalidCategory, invalidLevel);

        Mockito.verifyNoMoreInteractions(categoryStrategyMock, categoryAndLevelStrategyMock, defaultStrategyMock);
    }
}
