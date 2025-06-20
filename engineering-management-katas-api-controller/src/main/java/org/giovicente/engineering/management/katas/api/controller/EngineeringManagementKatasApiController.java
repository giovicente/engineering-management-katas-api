package org.giovicente.engineering.management.katas.api.controller;

import lombok.RequiredArgsConstructor;
import org.giovicente.engineering.management.katas.api.controller.dto.KataFilter;
import org.giovicente.engineering.management.katas.api.controller.mapper.KataDtoMapper;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Level;
import org.giovicente.engineering.management.katas.api.processor.strategy.handler.RandomKataHandler;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        if (category != null) {
            try {
                Category.valueOf(category.toUpperCase());
                category = category.toUpperCase();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid category: " + filter.getCategory());
            }
        }

        if (level != null) {
            try {
                Level.valueOf(level.toUpperCase());
                level = level.toUpperCase();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid level: " + filter.getLevel());
            }
        }

        var kata = handler.handle(category, level);
        return ResponseEntity.ok(dtoMapper.toResponse(kata));
    }
}
