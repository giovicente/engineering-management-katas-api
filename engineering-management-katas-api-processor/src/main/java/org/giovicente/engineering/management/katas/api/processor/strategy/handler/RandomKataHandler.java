package org.giovicente.engineering.management.katas.api.processor.strategy.handler;

import lombok.RequiredArgsConstructor;
import org.giovicente.engineering.management.katas.api.core.strategy.RandomKataStrategy;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RandomKataHandler {

    private final List<RandomKataStrategy> strategies;

    public Kata handle(Optional<String> category, Optional<String> level) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(category, level))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No strategy found for provided parameters"))
                .getKata(category, level);
    }
}
