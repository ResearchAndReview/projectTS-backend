package org.researchandreview.projecttsbackend.dto;

import lombok.Getter;
import org.researchandreview.projecttsbackend.model.ResultData;
import org.researchandreview.projecttsbackend.model.Task;
import org.researchandreview.projecttsbackend.model.TaskScoped;

import java.util.List;

@Getter
public class TaskStatusSuccessResponse extends TaskStatusResponse {
    private final TaskScoped task;
    private final List<ResultData> taskResults;

    public TaskStatusSuccessResponse(Task task, List<ResultData> taskResults) {
        super(task);
        this.task = new TaskScoped(task);
        this.taskResults = taskResults;
    }
}
