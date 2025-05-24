package org.giovicente.engineering.management.katas.api.controller;

import lombok.RequiredArgsConstructor;
import org.giovicente.engineering.management.katas.api.controller.mapper.KataDtoMapper;
import org.giovicente.engineering.management.katas.api.core.EngineeringManagementKatasApiProcessor;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/katas")
public class EngineeringManagementKatasApiController {

    private final EngineeringManagementKatasApiProcessor processor;
    private final KataDtoMapper dtoMapper;

    @GetMapping
    public ResponseEntity<?> getRandom(@RequestParam(name = "category", required = false) String category) {
        try {
            var kata = (category == null)
                    ? processor.getRandomKata()
                    : processor.getRandomKataByCategory(Category.valueOf(category.toUpperCase()));

            return ResponseEntity.ok(dtoMapper.toResponse(kata));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid category: " + category);
        }
    }
}
