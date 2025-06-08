package org.giovicente.engineering.management.katas.api.processor.tests;

import org.giovicente.engineering.management.katas.api.adapter.mapper.KataMapper;
import org.giovicente.engineering.management.katas.api.adapter.persistence.entity.KataEntity;
import org.giovicente.engineering.management.katas.api.adapter.persistence.repository.KataRepository;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Level;
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
    private KataEntity entity;
    private Kata model;

    @BeforeEach
    void setUp() {
        repository = mock(KataRepository.class);
        mapper = mock(KataMapper.class);
        processor = new EngineeringManagementKatasApiProcessorImpl(repository, mapper);

        entity = KataEntity.builder()
                .id(UUID.randomUUID())
                .category(Category.TECHNICAL)
                .title("Daily Stand-up kata")
                .description("How to improve your team's daily meeting?")
                .level(Level.EASY)
                .build();

        model = Kata.builder()
                .id(entity.getId())
                .category(entity.getCategory())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .level(entity.getLevel())
                .build();
    }

    @Test
    void getRandomKataByDefault_shouldReturnRandomKataWhenDataExists() {
        when(repository.findRandomKata()).thenReturn(entity);
        when(mapper.toModel(entity)).thenReturn(model);

        Kata actual = processor.getRandomKataByDefault();

        assertNotNull(actual);
        assertEquals("Daily Stand-up kata", actual.getTitle());
        verify(repository, times(1)).findRandomKata();
        verify(mapper, times(1)).toModel(entity);
    }

    @Test
    void getRandomKataByDefault_shouldThrowExceptionWhenNoRandomKatasFound() {
        when(repository.findRandomKata()).thenReturn(null);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> processor.getRandomKataByDefault());

        assertEquals("No katas found", exception.getMessage());
        verify(repository, times(1)).findRandomKata();
        verifyNoInteractions(mapper);
    }

    @Test
    void getRandomKataByCategory_shouldReturnRandomKataByCategoryWhenDataExists() {
        when(repository.findRandomKataByCategory(model.getCategory().toString())).thenReturn(entity);
        when(mapper.toModel(entity)).thenReturn(model);

        final String CATEGORY_REQUEST_PARAM = "TECHNICAL";

        Kata actual = processor.getRandomKataByCategory(Category.valueOf(CATEGORY_REQUEST_PARAM));

        assertNotNull(actual);
        assertEquals("Daily Stand-up kata", actual.getTitle());
        assertEquals(Category.TECHNICAL, actual.getCategory());
        verify(repository, times(1)).findRandomKataByCategory(CATEGORY_REQUEST_PARAM);
        verify(mapper, times(1)).toModel(entity);
    }

    @Test
    void getRandomKataByCategory_shouldThrowExceptionForInvalidCategory() {
        String invalidCategory = "SOCCER";
        when(repository.findRandomKataByCategory(invalidCategory)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> processor.getRandomKataByCategory(Category.valueOf(invalidCategory)));

        assertEquals("No enum constant org.giovicente.engineering.management.katas.api.domain.enums.Category." + invalidCategory, exception.getMessage());
        verify(repository, times(0)).findRandomKataByCategory(invalidCategory);
        verifyNoInteractions(mapper);
    }
}
