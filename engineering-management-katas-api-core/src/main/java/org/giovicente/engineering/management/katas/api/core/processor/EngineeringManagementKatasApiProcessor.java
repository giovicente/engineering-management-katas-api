package org.giovicente.engineering.management.katas.api.core.processor;

import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Level;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;

public interface EngineeringManagementKatasApiProcessor {
    Kata getRandomKataByDefault();
    Kata getRandomKataByCategory(Category category);
    Kata getRandomKataByLevel(Level level);
    Kata getRandomKataByCategoryAndLevel(Category category, Level level);
}
