package org.giovicente.engineering.management.katas.api.processor.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.giovicente.engineering.management.katas.api.core.processor.EngineeringManagementKatasApiProcessor;
import org.giovicente.engineering.management.katas.api.core.strategy.RandomKataStrategy;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Level;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryAndLevelRandomKataStrategy implements RandomKataStrategy {

    private final EngineeringManagementKatasApiProcessor processor;

    @Override
    public boolean supports(String category, String level) {
        return (category != null && !category.isBlank()) && (level != null && !level.isBlank());
    }

    @Override
    public Kata getKata(String category, String level) {
        return processor.getRandomKataByCategoryAndLevel(
                Category.valueOf(category),
                Level.valueOf(level)
        );
    }
}
