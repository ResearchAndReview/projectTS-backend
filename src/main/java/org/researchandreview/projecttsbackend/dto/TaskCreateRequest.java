package org.researchandreview.projecttsbackend.dto;

import lombok.Data;

@Data
public class TaskCreateRequest {
    private final String translateFrom;
    private final String translateTo;
}
