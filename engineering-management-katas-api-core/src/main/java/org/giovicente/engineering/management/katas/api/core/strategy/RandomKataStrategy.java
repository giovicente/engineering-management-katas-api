package org.giovicente.engineering.management.katas.api.core.strategy;

import org.giovicente.engineering.management.katas.api.domain.model.Kata;

import java.util.Optional;

public interface RandomKataStrategy {
    boolean supports(Optional<String> category, Optional<String> level);
    Kata getKata(Optional<String> category, Optional<String> level);
}
