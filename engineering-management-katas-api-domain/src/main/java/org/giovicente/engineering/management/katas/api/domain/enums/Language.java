package org.giovicente.engineering.management.katas.api.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Language {
    PT_BR("Portuguese (Brazil)"),
    EN_US("English (US)");

    private final String description;
}
