package org.giovicente.engineering.management.katas.api.controller.tests;

import org.giovicente.engineering.management.katas.api.controller.EngineeringManagementKatasApiController;
import org.giovicente.engineering.management.katas.api.controller.dto.KataResponse;
import org.giovicente.engineering.management.katas.api.controller.mapper.KataDtoMapper;
import org.giovicente.engineering.management.katas.api.core.EngineeringManagementKatasApiProcessor;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Language;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EngineeringManagementKatasApiControllerTest {

    private Kata model;
    private final KataDtoMapper mapper = Mappers.getMapper(KataDtoMapper.class);
    private EngineeringManagementKatasApiProcessor processorMock;
    private KataDtoMapper mapperMock;
    private KataResponse kataResponse;
    EngineeringManagementKatasApiController controller;

    @BeforeEach
    void setUp() {
        model = Kata.builder()
                .id(UUID.randomUUID())
                .category(Category.TECHNICAL)
                .language(Language.EN_US)
                .title("Daily Stand-up kata")
                .description("How to improve your team's daily meeting?")
                .build();

        processorMock = Mockito.mock(EngineeringManagementKatasApiProcessor.class);
        mapperMock = Mockito.mock(KataDtoMapper.class);

        kataResponse = new KataResponse(model.getCategory(), model.getTitle(), model.getDescription());
        controller = new EngineeringManagementKatasApiController(processorMock, mapperMock);
    }

    @Test
    void getRandom_shouldReturnKataResponse() {
        Mockito.when(processorMock.getRandomKataByDefault()).thenReturn(model);
        Mockito.when(mapperMock.toResponse(model)).thenReturn(kataResponse);

        ResponseEntity<KataResponse> response = (ResponseEntity<KataResponse>) controller.getRandom(null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Daily Stand-up kata");
        assertThat(response.getBody().getDescription()).isEqualTo("How to improve your team's daily meeting?");
    }

    @Test
    void toResponse_shouldMapTitleAndDescription() {
        KataResponse response = mapper.toResponse(model);

        assertThat(response).isNotNull();
        assertThat(response.getCategory()).isEqualTo(model.getCategory());
        assertThat(response.getTitle()).isEqualTo(model.getTitle());
        assertThat(response.getDescription()).isEqualTo(model.getDescription());
    }

    @Test
    void getRandom_shouldReturnKataResponseByCategory() {
        Mockito.when(processorMock.getRandomKataByCategory(model.getCategory())).thenReturn(model);
        Mockito.when(mapperMock.toResponse(model)).thenReturn(kataResponse);

        ResponseEntity<KataResponse> response = (ResponseEntity<KataResponse>) controller.getRandom(model.getCategory().toString());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Daily Stand-up kata");
        assertThat(response.getBody().getDescription()).isEqualTo("How to improve your team's daily meeting?");
    }

    @Test
    void getRandom_shouldThrowIllegalArgumentException() {
        String invalidCategory = "invalid";

        ResponseEntity<?> response = controller.getRandom(invalidCategory);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Invalid category: " + invalidCategory);
    }
}
