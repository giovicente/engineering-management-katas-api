package org.giovicente.engineering.management.katas.api.adapter.persistence.repository;

import org.giovicente.engineering.management.katas.api.adapter.persistence.entity.KataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface KataRepository extends JpaRepository<KataEntity, UUID> { }
