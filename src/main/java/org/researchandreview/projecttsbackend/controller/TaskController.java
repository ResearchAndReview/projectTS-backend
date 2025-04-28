package org.researchandreview.projecttsbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.researchandreview.projecttsbackend.dto.*;
import org.researchandreview.projecttsbackend.model.Task;
import org.researchandreview.projecttsbackend.service.TaskService;
import org.researchandreview.projecttsbackend.util.FileIOManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
/*
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
*/


    @Operation(summary = "작업 생성 : 이미지 업로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "작업 생성 성공",
                    content = @Content(schema = @Schema(implementation = TaskCreateSuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "필요한 리퀘스트 데이터 누락 또는 오류",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "작업 생성 실패",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TaskCreateSuccessResponse> postTaskCreate(
            @RequestPart TaskCreateRequest request,
            @RequestPart MultipartFile file,
            @RequestHeader(name = "x-uuid") String uuid) throws Exception {
        FileIOManager.saveMultipartFileToLocal(file);
        log.info(String.valueOf(request));
        int createdTaskId = taskService.createOneTask(
                file,
                uuid,
                request.getX(),
                request.getY(),
                request.getTranslateFrom(),
                request.getTranslateTo()); // DB Update
        taskService.createTaskMessage(file, createdTaskId); // send to AI Task Distributor
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new TaskCreateSuccessResponse("Task successfully created", createdTaskId));
    }

    @Operation(summary = "작업 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "작업 조회 성공",
                    content = @Content(schema = @Schema(implementation = TaskStatusSuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "작업 없음",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/status")
    public ResponseEntity<?> getTaskStatus(
            @RequestParam int taskId,
            @RequestHeader(name = "x-uuid") String uuid
    ) throws Exception {
        Task task = taskService.getTaskById(taskId, uuid);
        if (task == null) {
            throw new NotFoundException(taskId + " 작업을 찾을 수 없음");
        }
        log.info(task.toString());
        return switch (task.getStatus()) {
            case "SUCCESS" -> new ResponseEntity<TaskStatusSuccessResponse>(
                    new TaskStatusSuccessResponse(
                            task, taskService.handleSuccessTask(task)),
                    HttpStatus.OK);
            case "FAILED" -> new ResponseEntity<TaskStatusFailedResponse>(
                    new TaskStatusFailedResponse(
                            task
                    ),
                    HttpStatus.OK
            );
            default -> new ResponseEntity<TaskStatusResponse>(
                    new TaskStatusResponse(
                            task
                    ),
                    HttpStatus.OK
            );
        };
    }

    @PostMapping("/update")
    public ResponseEntity<GeneralResponse> postTaskUpdate(@RequestParam int taskId, @RequestBody GeneralRequest request) {
        return new ResponseEntity<>(new GeneralResponse("OK"), HttpStatus.OK);
    }

    @PostMapping("/recovery")
    public ResponseEntity<GeneralResponse> postTaskRecovery(@RequestParam int taskId, @RequestBody GeneralRequest request) {
        return new ResponseEntity<>(new GeneralResponse("OK"), HttpStatus.OK);
    }
}
