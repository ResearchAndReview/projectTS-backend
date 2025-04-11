package org.researchandreview.projecttsbackend.dto;

import lombok.Data;

@Data
public class TaskCreateRequest {
    private final int x;
    private final int y;
    private final String translateFrom;
    private final String translateTo;
}
