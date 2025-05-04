package org.researchandreview.projecttsbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.researchandreview.projecttsbackend.mapper.OCRResultMapper;
import org.researchandreview.projecttsbackend.mapper.OCRTaskMapper;
import org.researchandreview.projecttsbackend.mapper.TaskMapper;
import org.researchandreview.projecttsbackend.model.OCRResult;
import org.researchandreview.projecttsbackend.model.OCRTask;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OCRTaskService {

    private final TaskMapper taskMapper;
    private final OCRTaskMapper ocrTaskMapper;
    private final RabbitTemplate rabbitTemplate;
    private final OCRResultMapper ocrResultMapper;

    @Autowired
    public OCRTaskService(TaskMapper taskMapper, OCRTaskMapper ocrTaskMapper, RabbitTemplate rabbitTemplate, OCRResultMapper ocrResultMapper) {
        this.taskMapper = taskMapper;
        this.ocrTaskMapper = ocrTaskMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.ocrResultMapper = ocrResultMapper;
    }

    public OCRTask getOCRTaskById(int id) {
        return ocrTaskMapper.findOneOCRTaskById(id);
    }

    public void updateOCRTask(OCRTask ocrTask) {
        ocrTaskMapper.updateOneOCRTask(ocrTask);
    }

    public int createOCRTask(int taskId) {
        OCRTask ocrTask = new OCRTask();
        ocrTask.setTaskId(taskId);
        ocrTaskMapper.insertOneOCRTask(ocrTask);

        return ocrTask.getId();
    }

    public int createOCRResult(int ocrTaskId, int x, int y, int width, int height) {
        OCRResult ocrResult = new OCRResult();
        ocrResult.setOcrTaskId(ocrTaskId);
        ocrResult.setX(x);
        ocrResult.setY(y);
        ocrResult.setWidth(width);
        ocrResult.setHeight(height);
        ocrResultMapper.insertOneOCRResult(ocrResult);

        return ocrResult.getId();
    }

}
