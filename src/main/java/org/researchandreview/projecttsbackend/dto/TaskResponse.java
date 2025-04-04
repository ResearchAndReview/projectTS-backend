package org.researchandreview.projecttsbackend.dto;

import lombok.Getter;
import org.researchandreview.projecttsbackend.model.Task;

@Getter
public class TaskResponse extends GeneralReponse {
    private final Task task;


    public TaskResponse(String message, Task task) {
        super(message);
        this.task = task;
    }
}
