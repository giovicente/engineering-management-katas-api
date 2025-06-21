package org.giovicente.engineering.management.katas.api.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum Level {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private final String description;

    public static String validValues() {
        return Arrays.stream(values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }
}
