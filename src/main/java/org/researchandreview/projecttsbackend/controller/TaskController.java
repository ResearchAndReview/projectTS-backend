package org.researchandreview.projecttsbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.researchandreview.projecttsbackend.dto.GeneralReponse;
import org.researchandreview.projecttsbackend.dto.GeneralRequest;
import org.researchandreview.projecttsbackend.dto.TaskResponse;
import org.researchandreview.projecttsbackend.model.Task;
import org.researchandreview.projecttsbackend.service.TaskService;
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

    @GetMapping("/create")
    public ResponseEntity<GeneralReponse> taskCreateFromUser() {
        GeneralReponse generalReponse = new GeneralReponse("LLALD");
        log.info(generalReponse.getMessage());
        log.info(UUIDGenerator.generateUUID());
        return new ResponseEntity<>(generalReponse, HttpStatus.OK);
    }

    @PostMapping("/param-test")
    public ResponseEntity<GeneralReponse> taskCreateFromUserParam(@RequestParam String hello, @RequestParam int k) {
        log.info(String.valueOf(k));
        return new ResponseEntity<>(new GeneralReponse(hello), HttpStatus.OK);
    }

    @PostMapping("/body-test")
    public ResponseEntity<GeneralReponse> taskCreateFromUserBody(@ModelAttribute GeneralRequest request) {
        log.info(String.valueOf(request.getTest2()));
        return new ResponseEntity<>(new GeneralReponse(request.getTest()), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<TaskResponse> taskAll() {
        List<Task> taskList = taskService.getAllTasks();
        TaskResponse taskResponse = new TaskResponse("Successfully created.", taskList.get(0));
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @PostMapping("/hello")
    public ResponseEntity<GeneralReponse> createMessage(@RequestBody GeneralRequest request) {
        taskService.sendMessage(request.getTest());
        return new ResponseEntity<>(new GeneralReponse("OK"), HttpStatus.OK);

    }

    @PostMapping(path="/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GeneralReponse> taskUpload(@RequestPart String name, @RequestPart MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            long size = file.getSize();
            String uploadDir = System.getenv("TEMP_IMG_FOLDER");
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                if (!created) {
                    throw new IOException("디렉토리 생성 실패: " + uploadDir);
                }
            }
            file.transferTo(new File(uploadDir + "/" + fileName));
            return ResponseEntity.ok(new GeneralReponse("Hello" + name + " " + fileName + " " + size + "bytes?"));
        } catch (Exception e) {
            return new ResponseEntity<>(new GeneralReponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
