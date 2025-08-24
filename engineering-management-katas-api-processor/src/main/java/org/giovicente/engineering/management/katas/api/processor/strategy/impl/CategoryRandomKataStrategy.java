package org.giovicente.engineering.management.katas.api.processor.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.giovicente.engineering.management.katas.api.core.processor.EngineeringManagementKatasApiProcessor;
import org.giovicente.engineering.management.katas.api.core.strategy.RandomKataStrategy;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryRandomKataStrategy implements RandomKataStrategy {

    private final EngineeringManagementKatasApiProcessor processor;

    @Override
    public boolean supports(Optional<String> category, Optional<String> level) {
        return  category.isPresent() && level.isEmpty();
    }

    @Override
    public Kata getKata(Optional<String> category, Optional<String> level) {
        return processor.getRandomKataByCategory(
                Category.valueOf(category.orElse("Invalid Filter"))
        );
    }
}
