package org.giovicente.engineering.management.katas.api.domain.model;

import lombok.*;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Level;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Kata {
    private UUID id;
    private Category category;
    private String title;
    private String description;
    private Level level;
}
