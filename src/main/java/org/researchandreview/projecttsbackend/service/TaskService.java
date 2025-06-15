package org.researchandreview.projecttsbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.researchandreview.projecttsbackend.RabbitConfig;
import org.researchandreview.projecttsbackend.mapper.ImageMapper;
import org.researchandreview.projecttsbackend.mapper.OCRResultMapper;
import org.researchandreview.projecttsbackend.mapper.TaskMapper;
import org.researchandreview.projecttsbackend.mapper.TransResultMapper;
import org.researchandreview.projecttsbackend.model.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
public class TaskService {

    private final TaskMapper taskMapper;
    private final ImageMapper imageMapper;
    private final OCRResultMapper ocrResultMapper;
    private final TransResultMapper transResultMapper;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public TaskService(TaskMapper taskMapper, ImageMapper imageMapper, OCRResultMapper ocrResultMapper, TransResultMapper transResultMapper, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.taskMapper = taskMapper;
        this.imageMapper = imageMapper;
        this.ocrResultMapper = ocrResultMapper;
        this.transResultMapper = transResultMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public int createOneTask(MultipartFile imageFile, String uuid, int x, int y, String translateFrom, String translateTo) throws Exception {
        Task task = new Task();
        task.setTranslateTo(translateTo);
        task.setTranslateFrom(translateFrom);
        task.setOwnerNodeId(uuid);
        taskMapper.insertOneTask(task);

        BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());
        if (bufferedImage == null) {
            throw new Exception("Image load failed.");
        }
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        Image image = new Image();
        image.setWidth(width);
        image.setHeight(height);
        image.setX(x);
        image.setY(y);
        image.setTaskId(task.getId());
        // gather S3 Image URL
        // image.setImageURL(url);
        imageMapper.insertOneImage(image);


        return task.getId();
    }

    public List<ResultData> handleSuccessTask(Task task) {
        int taskId = task.getId();

        List<ResultData> taskResults = ocrResultMapper.findOCRResultsWithTransResultByTaskId(taskId);
        boolean isCompleted = true;
        if (taskResults == null || taskResults.isEmpty()) {
            isCompleted = false;
        }
        for (ResultData resultData : taskResults) {
            if (resultData.getTranslatedText() == null) {
                isCompleted = false;
                break;
            }
        }
        if(!task.getStatus().equals("failed")){
            if (isCompleted) {
                task.setStatus("success");
                taskMapper.updateOneTask(task);
            } else {
                task.setStatus("pending");
                taskMapper.updateOneTask(task);
            }
        }

        return taskResults;
    }

    public void updateTask(Task task) {
        taskMapper.updateOneTask(task);
    }

    public Task getTaskById(int id, String uuid) throws NotFoundException {
        try {
            return taskMapper.findOneTaskById(id, uuid);
        } catch (Exception e) {
            throw new NotFoundException(id + "에 해당하는 Task 없음");
        }
    }

    public Task getTaskByIdAdmin(int id) throws NotFoundException {
        try {
            return taskMapper.findOneTaskByIdAdmin(id);
        } catch (Exception e) {
            throw new NotFoundException(id + "에 해당하는 Task 없음");
        }
    }

    public void createOCRTaskMessage(MultipartFile file, int ocrTaskId) {
        try {
            byte[] fileBytes = file.getBytes();
            String base64Encoded = Base64.getEncoder().encodeToString(fileBytes);
            TaskMessage taskMessage = new TaskMessage();
            taskMessage.setTaskType(0);
            taskMessage.setOcrTaskId(ocrTaskId);
            taskMessage.setImageData(base64Encoded);

            String jsonMessage = objectMapper.writeValueAsString(taskMessage);

            rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NAME, jsonMessage);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

}
