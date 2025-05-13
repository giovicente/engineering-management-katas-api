package org.giovicente.engineering.management.katas.api.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Language;

import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Kata {
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
