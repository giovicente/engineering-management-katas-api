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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RandomKataHandlerTest {

    private RandomKataHandler handler;
    private RandomKataStrategy categoryStrategyMock;
    private RandomKataStrategy defaultStrategyMock;
    private RandomKataStrategy categoryAndLevelStrategyMock;

    private Kata kata;

    private Optional<String> category;
    private Optional<String> level;

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

        category = Optional.of("TECHNICAL");
        level = Optional.of("EASY");
    }

    @Test
    void handle_shouldUseCategoryStrategy() {
        Mockito.when(categoryStrategyMock.supports(category, Optional.empty())).thenReturn(true);
        Mockito.when(categoryStrategyMock.getKata(category, Optional.empty())).thenReturn(kata);

        Kata result = handler.handle(category, Optional.empty());

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Daily Stand-up kata");

        Mockito.verify(categoryStrategyMock).getKata(category, Optional.empty());

        Mockito.verify(defaultStrategyMock, Mockito.never()).getKata(Mockito.any(), Mockito.any());
        Mockito.verify(categoryAndLevelStrategyMock, Mockito.never()).getKata(Mockito.any(), Mockito.any());
    }

    @Test
    void handle_shouldUseDefaultStrategy() {
        Mockito.when(defaultStrategyMock.supports(Optional.empty(), Optional.empty())).thenReturn(true);
        Mockito.when(defaultStrategyMock.getKata(Optional.empty(), Optional.empty())).thenReturn(kata);

        Kata result = handler.handle(Optional.empty(), Optional.empty());

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Daily Stand-up kata");

        Mockito.verify(defaultStrategyMock).getKata(Optional.empty(), Optional.empty());

        Mockito.verify(categoryStrategyMock, Mockito.never()).getKata(Mockito.any(), Mockito.any());
        Mockito.verify(categoryAndLevelStrategyMock, Mockito.never()).getKata(Mockito.any(), Mockito.any());
    }

    @Test
    void handle_shouldUseCategoryAndLevelStrategy() {
        Mockito.when(categoryAndLevelStrategyMock.supports(category, level)).thenReturn(true);
        Mockito.when(categoryAndLevelStrategyMock.getKata(category, level)).thenReturn(kata);

        Kata result = handler.handle(category, level);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Daily Stand-up kata");

        Mockito.verify(categoryAndLevelStrategyMock).getKata(category, level);

        Mockito.verify(categoryStrategyMock, Mockito.never()).getKata(Mockito.any(), Mockito.any());
        Mockito.verify(defaultStrategyMock, Mockito.never()).getKata(Mockito.any(), Mockito.any());
    }

    @Test
    void handle_shouldThrowExceptionWhenNoStrategySupportsByCategory() {
        String invalidCategory = "SOCCER";

        Mockito.when(categoryStrategyMock.supports(invalidCategory.describeConstable(), Optional.empty())).thenReturn(false);
        Mockito.when(categoryAndLevelStrategyMock.supports(invalidCategory.describeConstable(), Optional.empty())).thenReturn(false);
        Mockito.when(defaultStrategyMock.supports(invalidCategory.describeConstable(), Optional.empty())).thenReturn(false);

        assertThatThrownBy(() -> handler.handle(invalidCategory.describeConstable(), Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No strategy found for provided parameters");

        Mockito.verify(categoryStrategyMock).supports(invalidCategory.describeConstable(), Optional.empty());
        Mockito.verify(categoryAndLevelStrategyMock).supports(invalidCategory.describeConstable(), Optional.empty());
        Mockito.verify(defaultStrategyMock).supports(invalidCategory.describeConstable(), Optional.empty());

        Mockito.verifyNoMoreInteractions(categoryStrategyMock, categoryAndLevelStrategyMock, defaultStrategyMock);
    }

    @Test
    void handle_shouldThrowExceptionWhenNoStrategySupportsByLevel() {
        String invalidLevel = "NIGHTMARE";

        Mockito.when(categoryStrategyMock.supports(Optional.empty(), invalidLevel.describeConstable())).thenReturn(false);
        Mockito.when(categoryAndLevelStrategyMock.supports(Optional.empty(), invalidLevel.describeConstable())).thenReturn(false);
        Mockito.when(defaultStrategyMock.supports(Optional.empty(), invalidLevel.describeConstable())).thenReturn(false);

        assertThatThrownBy(() -> handler.handle(Optional.empty(), invalidLevel.describeConstable()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No strategy found for provided parameters");

        Mockito.verify(categoryStrategyMock).supports(Optional.empty(), invalidLevel.describeConstable());
        Mockito.verify(categoryAndLevelStrategyMock).supports(Optional.empty(), invalidLevel.describeConstable());
        Mockito.verify(defaultStrategyMock).supports(Optional.empty(), invalidLevel.describeConstable());

        Mockito.verifyNoMoreInteractions(categoryStrategyMock, categoryAndLevelStrategyMock, defaultStrategyMock);
    }

    @Test
    void handle_shouldThrowExceptionWhenNoStrategySupportsByCategoryAndLevel() {
        String invalidCategory = "SOCCER";
        String invalidLevel = "NIGHTMARE";

        Mockito.when(categoryStrategyMock.supports(invalidCategory.describeConstable(), invalidLevel.describeConstable())).thenReturn(false);
        Mockito.when(categoryAndLevelStrategyMock.supports(invalidCategory.describeConstable(), invalidLevel.describeConstable())).thenReturn(false);
        Mockito.when(defaultStrategyMock.supports(invalidCategory.describeConstable(), invalidLevel.describeConstable())).thenReturn(false);

        assertThatThrownBy(() -> handler.handle(invalidCategory.describeConstable(), invalidLevel.describeConstable()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No strategy found for provided parameters");

        Mockito.verify(categoryStrategyMock).supports(invalidCategory.describeConstable(), invalidLevel.describeConstable());
        Mockito.verify(categoryAndLevelStrategyMock).supports(invalidCategory.describeConstable(), invalidLevel.describeConstable());
        Mockito.verify(defaultStrategyMock).supports(invalidCategory.describeConstable(), invalidLevel.describeConstable());

        Mockito.verifyNoMoreInteractions(categoryStrategyMock, categoryAndLevelStrategyMock, defaultStrategyMock);
    }
}
