package org.giovicente.engineering.management.katas.api.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Language;

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

    @Enumerated(EnumType.STRING)
    private Language language;

    private String title;
    private String description;
}
