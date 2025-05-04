package org.researchandreview.projecttsbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.researchandreview.projecttsbackend.mapper.TransResultMapper;
import org.researchandreview.projecttsbackend.model.TransTaskResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransTaskService {

    private final TransResultMapper transResultMapper;

    @Autowired
    public TransTaskService(TransResultMapper transResultMapper) {
        this.transResultMapper = transResultMapper;
    }

    public TransTaskResult getTransTaskById(int id) {
        return transResultMapper.findTransResultById(id);
    }

    public void updateTransTask(TransTaskResult transTask) {
        transResultMapper.updateOneTransTask(transTask);
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
