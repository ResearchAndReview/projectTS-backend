package org.researchandreview.projecttsbackend.dto;

import lombok.Getter;

@Getter
public class TaskCreateSuccessResponse extends GeneralResponse {
    private final int createdTaskId;

    public TaskCreateSuccessResponse(String message, int createdTaskId) {
        super(message);
        this.createdTaskId = createdTaskId;
    }
}
