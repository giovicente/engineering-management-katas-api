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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/katas")
public class EngineeringManagementKatasApiController {

    private final KataDtoMapper dtoMapper;
    private final RandomKataHandler handler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRandom(@ModelAttribute KataFilter filter) {
        Optional<String> category = Optional.ofNullable(filter.getCategory())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toUpperCase);

        Optional<String> level = Optional.ofNullable(filter.getLevel())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toUpperCase);

        List<String> errorDetails = new LinkedList<>();

        if (category.isPresent()) {
            try {
                Category.valueOf(category.orElse("Invalid Filter"));
            } catch (IllegalArgumentException e) {
                errorDetails.add("Invalid category informed: '" + filter.getCategory() + "'. Allowed values: " + Category.validValues());
            }
        }

        if (level.isPresent()) {
            try {
                Level.valueOf(level.orElse("Invalid Filter"));
            } catch (IllegalArgumentException e) {
                errorDetails.add("Invalid level informed: '" + filter.getLevel() + "'. Allowed values: " + Level.validValues());
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
