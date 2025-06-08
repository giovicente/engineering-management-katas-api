package org.giovicente.engineering.management.katas.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.giovicente.engineering.management.katas.api.domain.enums.Category;
import org.giovicente.engineering.management.katas.api.domain.enums.Level;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class KataResponse {
    private Category category;
    private String title;
    private String description;
    private Level level;
}
