package org.giovicente.engineering.management.katas.api.processor;

import lombok.RequiredArgsConstructor;
import org.giovicente.engineering.management.katas.api.adapter.mapper.KataMapper;
import org.giovicente.engineering.management.katas.api.adapter.persistence.repository.KataRepository;
import org.giovicente.engineering.management.katas.api.core.EngineeringManagementKatasApiProcessor;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EngineeringManagementKatasApiProcessorImpl implements EngineeringManagementKatasApiProcessor {

    private final KataRepository repository;
    private final KataMapper mapper;
    private final Random random = new Random();

    @Override
    public Kata getRandomKataUseCase() {
        var all = repository.findAll();
        if (all.isEmpty()) throw new IllegalStateException("No katas found");
        int randomIndex = random.nextInt(all.size());

        return mapper.toModel(all.get(randomIndex));
    }
}
