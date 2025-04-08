package org.researchandreview.projecttsbackend.dto;

import lombok.Getter;
import org.researchandreview.projecttsbackend.model.Task;

@Getter
public class TaskCreateSuccessfulResponse extends GeneralReponse {
    private final int createdTaskId;


    public TaskCreateSuccessfulResponse(String message, int createdTaskId) {
        super(message);

        this.createdTaskId = createdTaskId;
    }
}
