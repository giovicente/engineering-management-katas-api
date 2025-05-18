package org.giovicente.engineering.management.katas.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class KataResponse {
    private String title;
    private String description;
}
