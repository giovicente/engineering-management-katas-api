package org.giovicente.engineering.management.katas.api.controller;

import lombok.RequiredArgsConstructor;
import org.giovicente.engineering.management.katas.api.controller.dto.KataFilter;
import org.giovicente.engineering.management.katas.api.controller.error.ProblemResponseBuilder;
import org.giovicente.engineering.management.katas.api.controller.mapper.KataDtoMapper;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Level;
import org.giovicente.engineering.management.katas.api.processor.strategy.handler.RandomKataHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/katas")
public class EngineeringManagementKatasApiController {

    private final KataDtoMapper dtoMapper;
    private final RandomKataHandler handler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRandom(@ModelAttribute KataFilter filter) {
        String category = filter.getCategory();
        String level = filter.getLevel();

        List<String> errorDetails = new LinkedList<>();

        if (category != null) {
            try {
                Category.valueOf(category.toUpperCase());
                category = category.toUpperCase();
            } catch (IllegalArgumentException e) {
                errorDetails.add("Invalid category informed: '" + category + "'. Allowed values: " + Category.validValues());
            }
        }

        if (level != null) {
            try {
                Level.valueOf(level.toUpperCase());
                level = level.toUpperCase();
            } catch (IllegalArgumentException e) {
                errorDetails.add("Invalid level informed: '" + level + "'. Allowed values: " + Level.validValues());
            }
        }

        if (!errorDetails.isEmpty()) {
            return ProblemResponseBuilder.build(
                    HttpStatus.BAD_REQUEST,
                    "Invalid filter parameters",
                    "Please refer to the errors property for additional details",
                    errorDetails
            );
        }

        var kata = handler.handle(category, level);
        return ResponseEntity.ok(dtoMapper.toResponse(kata));
    }
}
