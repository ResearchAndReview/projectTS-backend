package org.researchandreview.projecttsbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.researchandreview.projecttsbackend.dto.GeneralReponse;
import org.researchandreview.projecttsbackend.dto.GeneralRequest;
import org.researchandreview.projecttsbackend.dto.TaskCreateSuccessfulResponse;
import org.researchandreview.projecttsbackend.dto.TaskResponse;
import org.researchandreview.projecttsbackend.model.Task;
import org.researchandreview.projecttsbackend.service.TaskService;
import org.researchandreview.projecttsbackend.util.FileIOManager;
import org.researchandreview.projecttsbackend.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public ResponseEntity<TaskResponse> taskAll() {
        List<Task> taskList = taskService.getAllTasks();
        log.info(taskList.toString());
        TaskResponse taskResponse = new TaskResponse("Successfully created.", taskList.get(0));
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @PostMapping("/hello")
    public ResponseEntity<GeneralReponse> createMessage(@RequestBody GeneralRequest request) {
        //taskService.sendMessage(request.getTest());
        return new ResponseEntity<>(new GeneralReponse("OK"), HttpStatus.OK);

    }

    @PostMapping(path="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GeneralReponse> taskUpload(@RequestPart int x, @RequestPart int y, @RequestPart MultipartFile file,
                                                     @RequestHeader(name = "x-uuid") String uuid) {
        try {
            FileIOManager.saveMultipartFileToLocal(file);
            int createdTaskId = taskService.createOneTask(file, uuid,x,y); // DB Update

            taskService.createTaskMessage(createdTaskId); // send to AI Task Distributor
            return ResponseEntity.ok(new TaskCreateSuccessfulResponse("Task Created", createdTaskId));
        } catch (Exception e) {
            return new ResponseEntity<>(new GeneralReponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
