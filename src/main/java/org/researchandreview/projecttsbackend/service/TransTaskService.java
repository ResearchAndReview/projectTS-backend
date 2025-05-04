package org.researchandreview.projecttsbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.researchandreview.projecttsbackend.mapper.OCRTaskMapper;
import org.researchandreview.projecttsbackend.mapper.TaskMapper;
import org.researchandreview.projecttsbackend.mapper.TransResultMapper;
import org.researchandreview.projecttsbackend.model.OCRTask;
import org.researchandreview.projecttsbackend.model.TransTaskResult;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransTaskService {

    private final TransResultMapper transResultMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TransTaskService(TransResultMapper transResultMapper, RabbitTemplate rabbitTemplate) {
        this.transResultMapper = transResultMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public int createTransTask(int ocrResultId, String originalText, String translateFrom, String translateTo) {
        TransTaskResult newTransTask = new TransTaskResult();
        newTransTask.setOcrResultId(ocrResultId);
        newTransTask.setOriginalText(originalText);
        newTransTask.setTranslateFrom(translateFrom);
        newTransTask.setTranslateTo(translateTo);

        transResultMapper.insertOneTransTask(newTransTask);

        return newTransTask.getId();
    }
}
