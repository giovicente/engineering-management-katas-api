package org.giovicente.engineering.management.katas.api.core;

import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;

public interface EngineeringManagementKatasApiProcessor {
    Kata getRandomKata();
    Kata getRandomKataByCategory(Category category);
}
