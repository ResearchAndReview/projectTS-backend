package org.researchandreview.projecttsbackend.dto;

import lombok.Getter;
import org.researchandreview.projecttsbackend.model.Task;

@Getter
public class TaskStatusFailedResponse extends TaskStatusResponse {
    private final String failCause;

    public TaskStatusFailedResponse(Task task) {
        super(task);
        this.failCause = task.getFailCause();
    }
}
