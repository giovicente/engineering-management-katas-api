package org.giovicente.engineering.management.katas.api.processor.tests.strategy.handler;

import org.giovicente.engineering.management.katas.api.core.strategy.RandomKataStrategy;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Language;
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

    private Kata kata;

    @BeforeEach
    void setUp() {
        categoryStrategyMock = Mockito.mock(RandomKataStrategy.class);
        defaultStrategyMock = Mockito.mock(RandomKataStrategy.class);

        List<RandomKataStrategy> strategies = Arrays.asList(categoryStrategyMock, defaultStrategyMock);
        handler = new RandomKataHandler(strategies);

        kata = Kata.builder()
                .id(UUID.randomUUID())
                .category(Category.TECHNICAL)
                .language(Language.EN_US)
                .title("Daily Stand-up kata")
                .description("How to improve your team's daily meeting?")
                .build();
    }

    @Test
    void handle_shouldUseCategoryStrategy() {
        Mockito.when(categoryStrategyMock.supports(Category.TECHNICAL.name())).thenReturn(true);
        Mockito.when(categoryStrategyMock.getKata(Category.TECHNICAL.name())).thenReturn(kata);

        Kata result = handler.handle(Category.TECHNICAL.name());

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Daily Stand-up kata");

        Mockito.verify(categoryStrategyMock).getKata(Category.TECHNICAL.name());
        Mockito.verifyNoInteractions(defaultStrategyMock);
    }

    @Test
    void handle_shouldUseDefaultStrategy() {
        Mockito.when(defaultStrategyMock.supports(null)).thenReturn(true);
        Mockito.when(defaultStrategyMock.getKata(null)).thenReturn(kata);

        Kata result = handler.handle(null);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Daily Stand-up kata");

        Mockito.verify(defaultStrategyMock).getKata(null);

        Mockito.verify(categoryStrategyMock).supports(null);
        Mockito.verify(categoryStrategyMock, Mockito.never()).getKata(Mockito.any());
        Mockito.verifyNoMoreInteractions(categoryStrategyMock);
    }

    @Test
    void handle_shouldThrowExceptionWhenNoStrategySupports() {
        String invalidCategory = "SOCCER";

        Mockito.when(categoryStrategyMock.supports(invalidCategory)).thenReturn(false);
        Mockito.when(defaultStrategyMock.supports(invalidCategory)).thenReturn(false);

        assertThatThrownBy(() -> handler.handle(invalidCategory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No strategy found for provided parameters");

        Mockito.verify(categoryStrategyMock).supports(invalidCategory);
        Mockito.verify(defaultStrategyMock).supports(invalidCategory);

        Mockito.verifyNoMoreInteractions(categoryStrategyMock, defaultStrategyMock);
    }
}
