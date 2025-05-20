package org.researchandreview.projecttsbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.researchandreview.projecttsbackend.RabbitConfig;
import org.researchandreview.projecttsbackend.mapper.TransResultMapper;
import org.researchandreview.projecttsbackend.model.TaskMessage;
import org.researchandreview.projecttsbackend.model.TransTaskResult;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransTaskService {

    private final TransResultMapper transResultMapper;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public TransTaskService(TransResultMapper transResultMapper, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.transResultMapper = transResultMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public TransTaskResult getTransTaskById(int id) {
        return transResultMapper.findTransResultById(id);
    }

    public void updateTransTask(TransTaskResult transTask) {
        transResultMapper.updateOneTransTask(transTask);
    }

    public TransTaskResult createTransTask(int ocrResultId, String originalText, String translateFrom, String translateTo) {
        TransTaskResult newTransTask = new TransTaskResult();
        newTransTask.setOcrResultId(ocrResultId);
        newTransTask.setOriginalText(originalText);
        newTransTask.setTranslateFrom(translateFrom);
        newTransTask.setTranslateTo(translateTo);

        transResultMapper.insertOneTransTask(newTransTask);

        return newTransTask;
    }


    public void createTransTaskMessage(TransTaskResult transTaskResult) {
        try {

            TaskMessage taskMessage = new TaskMessage();
            taskMessage.setTaskType(1);
            taskMessage.setTransTaskId(transTaskResult.getId());

            taskMessage.setOcrResultId(transTaskResult.getOcrResultId());
            taskMessage.setOriginalText(transTaskResult.getOriginalText());
            taskMessage.setTranslateFrom(transTaskResult.getTranslateFrom());
            taskMessage.setTranslateTo(transTaskResult.getTranslateTo());

            String jsonMessage = objectMapper.writeValueAsString(taskMessage);

            rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NAME, jsonMessage);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
