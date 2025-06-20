package org.giovicente.engineering.management.katas.api.adapter.persistence.repository;

import org.giovicente.engineering.management.katas.api.adapter.persistence.entity.KataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface KataRepository extends JpaRepository<KataEntity, UUID> {

    @Query(value = "SELECT * FROM kata ORDER BY random() LIMIT 1", nativeQuery = true)
    KataEntity findRandomKata();

    @Query(value = "SELECT * FROM kata WHERE category = ?1 ORDER BY random() LIMIT 1", nativeQuery = true)
    KataEntity findRandomKataByCategory(String category);

    @Query(value = "SELECT * FROM kata WHERE level = ?1 ORDER BY random() LIMIT 1", nativeQuery = true)
    KataEntity findRandomKataByLevel(String level);
}
