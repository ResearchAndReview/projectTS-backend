package org.researchandreview.projecttsbackend.service;

import org.researchandreview.projecttsbackend.RabbitConfig;
import org.researchandreview.projecttsbackend.model.Task;
import org.researchandreview.projecttsbackend.repository.TaskRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TaskService(TaskRepository taskRepository, RabbitTemplate rabbitTemplate) {
        this.taskRepository = taskRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public void sendMessage(String msg){
        Message message = new Message(msg.getBytes());
        rabbitTemplate.send(RabbitConfig.QUEUE_NAME, message);
    }
}
