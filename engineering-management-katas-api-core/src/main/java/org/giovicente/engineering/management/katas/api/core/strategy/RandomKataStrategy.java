package org.giovicente.engineering.management.katas.api.core.strategy;

import org.giovicente.engineering.management.katas.api.domain.model.Kata;

public interface RandomKataStrategy {
    boolean supports(String category, String level);
    Kata getKata(String category, String level);
}
