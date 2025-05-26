package org.giovicente.engineering.management.katas.api.controller.tests;

import org.giovicente.engineering.management.katas.api.controller.EngineeringManagementKatasApiController;
import org.giovicente.engineering.management.katas.api.controller.dto.KataResponse;
import org.giovicente.engineering.management.katas.api.controller.mapper.KataDtoMapper;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Language;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.giovicente.engineering.management.katas.api.processor.strategy.handler.RandomKataHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class EngineeringManagementKatasApiControllerTest {

    private MockMvc mockMvc;

    private KataDtoMapper dtoMapper;
    private RandomKataHandler handler;

    private Kata kata;
    private KataResponse kataResponse;

    @BeforeEach
    void setUp() {
        handler = Mockito.mock(RandomKataHandler.class);
        dtoMapper = Mockito.mock(KataDtoMapper.class);

        EngineeringManagementKatasApiController controller
                = new EngineeringManagementKatasApiController(dtoMapper, handler);

        mockMvc = standaloneSetup(controller).build();

        kata = Kata.builder()
                .id(UUID.randomUUID())
                .category(Category.TECHNICAL)
                .language(Language.EN_US)
                .title("Daily Stand-up kata")
                .description("How to improve your team's daily meeting?")
                .build();

        kataResponse = new KataResponse(kata.getCategory(), kata.getTitle(), kata.getDescription());
    }

    @Test
    void getRandom_withoutCategory_shouldReturnOk() throws Exception {
        Mockito.when(handler.handle(null)).thenReturn(kata);
        Mockito.when(dtoMapper.toResponse(kata)).thenReturn(kataResponse);

        mockMvc.perform(get("/katas")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Daily Stand-up kata"));
    }

    @Test
    void getRandom_withValidCategory_shouldReturnOk() throws Exception {
        Mockito.when(handler.handle(String.valueOf(Category.TECHNICAL))).thenReturn(kata);
        Mockito.when(dtoMapper.toResponse(kata)).thenReturn(kataResponse);

        mockMvc.perform(get("/katas")
                        .param("category", "technical")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Daily Stand-up kata"));
    }

    @Test
    void getRandom_withInvalidCategory_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/katas")
                        .param("category", "invalidCategory")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid category: invalidCategory"));
    }
}
