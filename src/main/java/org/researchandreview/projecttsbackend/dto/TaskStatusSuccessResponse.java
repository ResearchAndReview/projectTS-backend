package org.researchandreview.projecttsbackend.dto;

import lombok.Getter;
import org.researchandreview.projecttsbackend.model.Task;
import org.researchandreview.projecttsbackend.model.ResultData;

import java.util.List;
import java.util.Map;

@Getter
public class TaskStatusSuccessResponse extends TaskStatusResponse {
    private final Map<String, Object> resultData;

    public TaskStatusSuccessResponse(Task task, Map<String, Object> resultData) {
        super(task);
        this.resultData = resultData;
    }
}
