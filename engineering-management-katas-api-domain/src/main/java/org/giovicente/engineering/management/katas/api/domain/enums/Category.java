package org.giovicente.engineering.management.katas.api.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
    BEHAVIORAL("Behavioral"),
    TECHNICAL("Technical"),
    STRATEGIC("Strategic");

    private final String description;
}
