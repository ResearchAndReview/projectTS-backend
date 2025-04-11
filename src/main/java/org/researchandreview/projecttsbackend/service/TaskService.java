package org.researchandreview.projecttsbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.researchandreview.projecttsbackend.RabbitConfig;
import org.researchandreview.projecttsbackend.mapper.ImageMapper;
import org.researchandreview.projecttsbackend.mapper.TaskMapper;
import org.researchandreview.projecttsbackend.model.Image;
import org.researchandreview.projecttsbackend.model.Task;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;

@Slf4j
@Service
public class TaskService {

    private final TaskMapper taskMapper;
    private final ImageMapper imageMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TaskService(TaskMapper taskMapper, ImageMapper imageMapper, RabbitTemplate rabbitTemplate) {
        this.taskMapper = taskMapper;
        this.imageMapper = imageMapper;
        this.rabbitTemplate = rabbitTemplate;
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


    public List<Task> getAllTasks() {
        return taskMapper.findAllTasks();
    }

    public Task getTaskById(int id, String uuid) throws NotFoundException {
        try {
            return taskMapper.findOneTaskById(id, uuid);
        } catch (Exception e) {
            throw new NotFoundException(id + "에 해당하는 Task 없음");
        }
    }

    public void createTaskMessage(int taskId) {
        Message message = new Message(String.valueOf(taskId).getBytes());
        rabbitTemplate.send(RabbitConfig.QUEUE_NAME, message);
    }
}
