package org.giovicente.engineering.management.katas.api.processor;

import lombok.RequiredArgsConstructor;
import org.giovicente.engineering.management.katas.api.adapter.mapper.KataMapper;
import org.giovicente.engineering.management.katas.api.adapter.persistence.repository.KataRepository;
import org.giovicente.engineering.management.katas.api.core.processor.EngineeringManagementKatasApiProcessor;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.model.Kata;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EngineeringManagementKatasApiProcessorImpl implements EngineeringManagementKatasApiProcessor {

    private final KataRepository repository;
    private final KataMapper mapper;

    @Override
    public Kata getRandomKataByDefault() {
        var entity = repository.findRandomKata();
        if (entity == null) throw new IllegalStateException("No katas found");
        return mapper.toModel(entity);
    }

    @Override
    public Kata getRandomKataByCategory(Category category) {
        var entity = repository.findRandomKataByCategory(String.valueOf(category));
        return mapper.toModel(entity);
    }
}
