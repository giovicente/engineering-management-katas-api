package org.giovicente.engineering.management.katas.api.controller.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiProblem {
    private String type;
    private String title;
    private int status;
    private String detail;
    private List<String> errors;
    private OffsetDateTime timestamp;
}
