package org.giovicente.engineering.management.katas.api.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Level;

import java.util.UUID;

@Entity
@Table(name = "kata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KataEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;

    @Column(length = 550)
    private String description;

    @Enumerated(EnumType.STRING)
    private Level level;
}
