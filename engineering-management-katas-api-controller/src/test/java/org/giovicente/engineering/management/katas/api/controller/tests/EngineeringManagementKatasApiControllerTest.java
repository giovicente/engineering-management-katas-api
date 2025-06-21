package org.giovicente.engineering.management.katas.api.controller.tests;

import org.giovicente.engineering.management.katas.api.controller.EngineeringManagementKatasApiController;
import org.giovicente.engineering.management.katas.api.controller.dto.KataFilter;
import org.giovicente.engineering.management.katas.api.controller.dto.KataResponse;
import org.giovicente.engineering.management.katas.api.controller.error.ApiProblem;
import org.giovicente.engineering.management.katas.api.controller.mapper.KataDtoMapper;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Level;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.giovicente.engineering.management.katas.api.processor.strategy.handler.RandomKataHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
                .title("Daily Stand-up kata")
                .description("How to improve your team's daily meeting?")
                .level(Level.EASY)
                .build();

        kataResponse = new KataResponse(kata.getCategory(), kata.getTitle(), kata.getDescription(), kata.getLevel());
    }

    @Test
    void getRandom_withoutCategoryAndLevel_shouldReturnOk() throws Exception {
        Mockito.when(handler.handle(null, null)).thenReturn(kata);
        Mockito.when(dtoMapper.toResponse(kata)).thenReturn(kataResponse);

        mockMvc.perform(get("/katas")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value(String.valueOf(Category.TECHNICAL)))
                .andExpect(jsonPath("$.title").value("Daily Stand-up kata"))
                .andExpect(jsonPath("$.description").value("How to improve your team's daily meeting?"))
                .andExpect(jsonPath("$.level").value(String.valueOf(Level.EASY)));

    }

    @Test
    void getRandom_withValidCategory_shouldReturnOk() throws Exception {
        Mockito.when(handler.handle(String.valueOf(Category.TECHNICAL), null)).thenReturn(kata);
        Mockito.when(dtoMapper.toResponse(kata)).thenReturn(kataResponse);

        mockMvc.perform(get("/katas")
                        .param("category", "technical")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value(String.valueOf(Category.TECHNICAL)))
                .andExpect(jsonPath("$.title").value("Daily Stand-up kata"))
                .andExpect(jsonPath("$.description").value("How to improve your team's daily meeting?"))
                .andExpect(jsonPath("$.level").value(String.valueOf(Level.EASY)));
    }

    @Test
    void getRandom_withValidLevel_shouldReturnOk() throws Exception {
        Mockito.when(handler.handle(null, String.valueOf(Level.EASY))).thenReturn(kata);
        Mockito.when(dtoMapper.toResponse(kata)).thenReturn(kataResponse);

        mockMvc.perform(get("/katas")
                        .param("level", "easy")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value(String.valueOf(Category.TECHNICAL)))
                .andExpect(jsonPath("$.title").value("Daily Stand-up kata"))
                .andExpect(jsonPath("$.description").value("How to improve your team's daily meeting?"))
                .andExpect(jsonPath("$.level").value(String.valueOf(Level.EASY)));
    }

    @Test
    void getRandom_withValidInput_shouldReturnOk() throws Exception {
        Mockito.when(handler.handle(String.valueOf(Category.TECHNICAL), String.valueOf(Level.EASY))).thenReturn(kata);
        Mockito.when(dtoMapper.toResponse(kata)).thenReturn(kataResponse);

        mockMvc.perform(get("/katas")
                        .param("category", "technical")
                        .param("level", "easy")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value(String.valueOf(Category.TECHNICAL)))
                .andExpect(jsonPath("$.title").value("Daily Stand-up kata"))
                .andExpect(jsonPath("$.description").value("How to improve your team's daily meeting?"))
                .andExpect(jsonPath("$.level").value(String.valueOf(Level.EASY)));
    }

    @Test
    void getRandom_withInvalidInput_shouldReturnProblemDetail() {
        KataFilter filter = new KataFilter("SOCCER", "NIGHTMARE");

        EngineeringManagementKatasApiController controller
                = new EngineeringManagementKatasApiController(dtoMapper, handler);

        ResponseEntity<?> response = controller.getRandom(filter);

        final int HTTP_STATUS_CODE_FOR_BAD_REQUEST = 400;

        assertEquals(HTTP_STATUS_CODE_FOR_BAD_REQUEST, Integer.valueOf(response.getStatusCode().toString().substring(0, 3)));
        assertInstanceOf(ApiProblem.class, response.getBody());

        ApiProblem problem = (ApiProblem) response.getBody();

        assertEquals("Invalid filter parameters", problem.getTitle());
        assertEquals(HTTP_STATUS_CODE_FOR_BAD_REQUEST, problem.getStatus());
        assertEquals("Please refer to the errors property for additional details", problem.getDetail());

        List<String> errors = problem.getErrors();
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.stream().anyMatch(msg -> msg.contains("Invalid category")));
        assertTrue(errors.stream().anyMatch(msg -> msg.contains("Invalid level")));
    }

    @Test
    void getRandom_withInvalidCategory_shouldReturnProblemDetail() {
        KataFilter filter = new KataFilter("SOCCER", "HARD");

        EngineeringManagementKatasApiController controller
                = new EngineeringManagementKatasApiController(dtoMapper, handler);

        ResponseEntity<?> response = controller.getRandom(filter);

        final int HTTP_STATUS_CODE_FOR_BAD_REQUEST = 400;

        assertEquals(HTTP_STATUS_CODE_FOR_BAD_REQUEST, Integer.valueOf(response.getStatusCode().toString().substring(0, 3)));
        assertInstanceOf(ApiProblem.class, response.getBody());

        ApiProblem problem = (ApiProblem) response.getBody();

        assertEquals("Invalid filter parameters", problem.getTitle());
        assertEquals(HTTP_STATUS_CODE_FOR_BAD_REQUEST, problem.getStatus());
        assertEquals("Please refer to the errors property for additional details", problem.getDetail());

        List<String> errors = problem.getErrors();
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.stream().anyMatch(msg -> msg.contains("Invalid category")));
    }

    @Test
    void getRandom_withInvalidLevel_shouldReturnProblemDetail() {
        KataFilter filter = new KataFilter("STRATEGIC", "NIGHTMARE");

        EngineeringManagementKatasApiController controller
                = new EngineeringManagementKatasApiController(dtoMapper, handler);

        ResponseEntity<?> response = controller.getRandom(filter);

        final int HTTP_STATUS_CODE_FOR_BAD_REQUEST = 400;

        assertEquals(HTTP_STATUS_CODE_FOR_BAD_REQUEST, Integer.valueOf(response.getStatusCode().toString().substring(0, 3)));
        assertInstanceOf(ApiProblem.class, response.getBody());

        ApiProblem problem = (ApiProblem) response.getBody();

        assertEquals("Invalid filter parameters", problem.getTitle());
        assertEquals(HTTP_STATUS_CODE_FOR_BAD_REQUEST, problem.getStatus());
        assertEquals("Please refer to the errors property for additional details", problem.getDetail());

        List<String> errors = problem.getErrors();
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.stream().anyMatch(msg -> msg.contains("Invalid level")));
    }
}
