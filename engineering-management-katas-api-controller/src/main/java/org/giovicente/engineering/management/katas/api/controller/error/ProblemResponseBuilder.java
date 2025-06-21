package org.giovicente.engineering.management.katas.api.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.List;

public class ProblemResponseBuilder {

    public static ResponseEntity<ApiProblem> build(HttpStatus status, String title, String detail, List<String> errors) {
        ApiProblem problem = ApiProblem.builder()
                .type("https://httpstatuses.com/" + status.value())
                .title(title)
                .status(status.value())
                .detail(detail)
                .errors(errors)
                .timestamp(OffsetDateTime.now())
                .build();

        return ResponseEntity.status(status).body(problem);
    }
}
