package org.giovicente.engineering.management.katas.api.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Level {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private final String description;
}
