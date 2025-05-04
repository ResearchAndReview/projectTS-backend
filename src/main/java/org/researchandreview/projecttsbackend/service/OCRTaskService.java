package org.researchandreview.projecttsbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.researchandreview.projecttsbackend.RabbitConfig;
import org.researchandreview.projecttsbackend.mapper.*;
import org.researchandreview.projecttsbackend.model.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public OCRTask getOCRTaskById(String taskId) {
        //ocrTaskMapper.
    }

    public int createOCRTask(int taskId) {
        OCRTask ocrTask = new OCRTask();
        ocrTask.setTaskId(taskId);
        ocrTaskMapper.insertOneOCRTask(ocrTask);

        return ocrTask.getId();
    }

    public int createOCRResult(int ocrTaskId, int x, int y, int width, int height){
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
