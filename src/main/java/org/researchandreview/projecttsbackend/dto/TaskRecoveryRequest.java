package org.researchandreview.projecttsbackend.dto;

import lombok.Data;

@Data
public class TaskRecoveryRequest {
    private final int ocrResultId;
    private final int transResultId;
    private final String originalText;
    private final String translateFrom;
    private final String translateTo;
}
