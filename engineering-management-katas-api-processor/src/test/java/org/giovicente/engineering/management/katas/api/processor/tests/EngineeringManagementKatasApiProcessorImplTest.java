package org.giovicente.engineering.management.katas.api.processor.tests;

import org.giovicente.engineering.management.katas.api.adapter.mapper.KataMapper;
import org.giovicente.engineering.management.katas.api.adapter.persistence.entity.KataEntity;
import org.giovicente.engineering.management.katas.api.adapter.persistence.repository.KataRepository;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Language;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.giovicente.engineering.management.katas.api.processor.EngineeringManagementKatasApiProcessorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EngineeringManagementKatasApiProcessorImplTest {

    private KataRepository repository;
    private KataMapper mapper;
    private EngineeringManagementKatasApiProcessorImpl processor;

    @BeforeEach
    void setUp() {
        repository = mock(KataRepository.class);
        mapper = mock(KataMapper.class);
        processor = new EngineeringManagementKatasApiProcessorImpl(repository, mapper);
    }

    @Test
    void shouldReturnRandomKataWhenDataExists() {
        KataEntity entity = KataEntity.builder()
                .id(UUID.randomUUID())
                .category(Category.TECHNICAL)
                .language(Language.EN_US)
                .title("Daily Stand-up kata")
                .description("How to improve your team's daily meeting?")
                .build();

        Kata model = Kata.builder()
                .id(entity.getId())
                .category(entity.getCategory())
                .language(entity.getLanguage())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .build();

        when(repository.findRandomKata()).thenReturn(entity);
        when(mapper.toModel(entity)).thenReturn(model);

        Kata actual = processor.getRandomKata();

        assertNotNull(actual);
        assertEquals("Daily Stand-up kata", actual.getTitle());
        verify(repository, times(1)).findRandomKata();
        verify(mapper, times(1)).toModel(entity);
    }

    @Test
    void shouldThrowExceptionWhenNoRandomKatasFound() {
        when(repository.findRandomKata()).thenReturn(null);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> processor.getRandomKata());

        assertEquals("No katas found", exception.getMessage());
        verify(repository, times(1)).findRandomKata();
        verifyNoInteractions(mapper);
    }
}
