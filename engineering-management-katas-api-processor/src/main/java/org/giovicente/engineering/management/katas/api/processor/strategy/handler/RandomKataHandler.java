package org.giovicente.engineering.management.katas.api.processor.strategy.handler;

import lombok.RequiredArgsConstructor;
import org.giovicente.engineering.management.katas.api.core.strategy.RandomKataStrategy;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RandomKataHandler {

    private final List<RandomKataStrategy> strategies;

    public Kata handle(String category) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(category))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No strategy found for provided parameters"))
                .getKata(category);
    }
}
