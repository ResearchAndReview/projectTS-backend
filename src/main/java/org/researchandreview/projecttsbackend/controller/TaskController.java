package org.researchandreview.projecttsbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.javassist.NotFoundException;
import org.researchandreview.projecttsbackend.dto.*;
import org.researchandreview.projecttsbackend.model.*;
import org.researchandreview.projecttsbackend.service.NodeService;
import org.researchandreview.projecttsbackend.service.OCRTaskService;
import org.researchandreview.projecttsbackend.service.TaskService;
import org.researchandreview.projecttsbackend.service.TransTaskService;
import org.researchandreview.projecttsbackend.util.FileIOManager;
import org.researchandreview.projecttsbackend.util.PerformanceManager;
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

    private final NodeService nodeService;
    private final TaskService taskService;
    private final OCRTaskService ocrTaskService;
    private final TransTaskService transTaskService;
    private final PerformanceManager performanceManager;

    @Autowired
    public TaskController(TaskService taskService, OCRTaskService ocrTaskService, TransTaskService transTaskService, NodeService nodeService, PerformanceManager performanceManager) {
        this.taskService = taskService;
        this.ocrTaskService = ocrTaskService;
        this.transTaskService = transTaskService;
        this.nodeService = nodeService;
        this.performanceManager = performanceManager;
    }


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
                0,
                0,
                request.getTranslateFrom(),
                request.getTranslateTo());
        int createdOCRTaskId = ocrTaskService.createOCRTask(createdTaskId);

        taskService.createOCRTaskMessage(file, createdOCRTaskId); // send to AI Task Distributor
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
    public ResponseEntity<TaskStatusSuccessResponse> getTaskStatus(
            @RequestParam int taskId,
            @RequestHeader(name = "x-uuid") String uuid
    ) throws Exception {
        Task task = taskService.getTaskById(taskId, uuid);
        if (task == null) {
            throw new NotFoundException(taskId + " 작업을 찾을 수 없음");
        }
        log.info(task.toString());
        return new ResponseEntity<>(
                new TaskStatusSuccessResponse(
                        task, taskService.handleSuccessTask(task)),
                HttpStatus.OK);
        /*return switch (task.getStatus()) {
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
        };*/
    }

    @PostMapping("/notify/ocr-success")
    public ResponseEntity<GeneralResponse> postNotifyOCRSuccess(
            @RequestHeader(name = "x-uuid") String uuid,
            @RequestParam int ocrTaskId,
            @RequestBody TaskNotifyOCRSuccessRequest request
    ) throws Exception {
        Node node = nodeService.getOneNodeById(uuid);
        if (node == null) {
            throw new NotFoundException(uuid + " 노드를 찾을 수 없음");
        }
        node.setStatus("IDLE");
        OCRTask ocrTask = ocrTaskService.getOCRTaskById(ocrTaskId);
        if (ocrTask == null) {
            throw new NotFoundException(ocrTaskId + " OCR 작업을 찾을 수 없음");
        }
        // log.info(task.toString());
        Task task = taskService.getTaskByIdAdmin(ocrTask.getTaskId());

        // decrease ocr task size
        node.setOcrTaskSize(node.getOcrTaskSize() - request.getOcrTaskSize());

        // recacurate ocr performance
        double oldOcrPerf = node.getOcrPerf();
        double calcuratedOcrPerf = request.getOcrTaskSize() / request.getElapsedTime();
        double newOcrPerf = performanceManager.calcurateNewPerformance(oldOcrPerf, calcuratedOcrPerf);
        node.setOcrPerf(newOcrPerf);

        // List<Integer> createdOCRResultId = new ArrayList<>();
      
        for (Caption caption : request.getCaptions()) {
            int ocrResultId = ocrTaskService.createOCRResult(ocrTaskId, caption.getX(), caption.getY(), caption.getWidth(), caption.getHeight(), caption.getText());
            TransTaskResult transTaskResult = transTaskService.createTransTask(ocrResultId, caption.getText(), task.getTranslateFrom(), task.getTranslateTo());
            transTaskService.createTransTaskMessage(transTaskResult);
        }

        nodeService.updateOneNode(node);
        ocrTask.setStatus("success");
        ocrTaskService.updateOCRTask(ocrTask);

        return new ResponseEntity<>(new GeneralResponse("OK"), HttpStatus.OK);
    }

    @PostMapping("/notify/trans-success")
    public ResponseEntity<GeneralResponse> postNotifyTransSuccess(
            @RequestHeader(name = "x-uuid") String uuid,
            @RequestParam int transTaskId,
            @RequestBody TaskNotifyTransSuccessRequest request
    ) throws Exception {
        Node node = nodeService.getOneNodeById(uuid);
        if (node == null) {
            throw new NotFoundException(uuid + " 노드를 찾을 수 없음");
        }
        node.setStatus("IDLE");
        TransTaskResult transTask = transTaskService.getTransTaskById(transTaskId);
        if (transTask == null) {
            throw new NotFoundException(transTaskId + " 번역 작업을 찾을 수 없음");
        }
        // log.info(task.toString());

        // decrease trans task size
        node.setTransTaskSize(node.getTransTaskSize() - request.getTransTaskSize());

        // recacurate trans performance
        double oldTransPerf = node.getTransPerf();
        double calcuratedTransPerf = request.getTransTaskSize() / request.getElapsedTime();
        double newTransPerf = performanceManager.calcurateNewPerformance(oldTransPerf, calcuratedTransPerf);
        node.setTransPerf(newTransPerf);

        nodeService.updateOneNode(node);
      
        transTask.setTranslatedText(request.getTranslatedText());
        transTask.setStatus("success");
        transTaskService.updateTransTask(transTask);

        return new ResponseEntity<>(new GeneralResponse("Trans Success Reported"), HttpStatus.OK);
    }

    @PostMapping("/notify/ocr-failed")
    public ResponseEntity<GeneralResponse> postNotifyOCRFailed(
            @RequestHeader(name = "x-uuid") String uuid,
            @RequestParam int ocrTaskId,
            @RequestBody TaskNotifyFailedRequest request
    ) throws NotFoundException {
        Node node = nodeService.getOneNodeById(uuid);
        if (node == null) {
            throw new NotFoundException(uuid + " 노드를 찾을 수 없음");
        }
        node.setStatus("IDLE");
        OCRTask ocrTask = ocrTaskService.getOCRTaskById(ocrTaskId);
        if (ocrTask == null) {
            throw new NotFoundException(ocrTaskId + " OCR 작업을 찾을 수 없음");
        }
        nodeService.updateOneNode(node);
        Task task = taskService.getTaskByIdAdmin(ocrTask.getTaskId());
        task.setStatus("failed");
        task.setFailCause(request.getError());
        taskService.updateTask(task);
        ocrTask.setStatus("failed");
        ocrTask.setFailCause(request.getError());
        ocrTaskService.updateOCRTask(ocrTask);
        return new ResponseEntity<>(new GeneralResponse("OCR Failed Reported"), HttpStatus.OK);
    }

    @PostMapping("/notify/trans-failed")
    public ResponseEntity<GeneralResponse> postNotifyTransFailed(
            @RequestHeader(name = "x-uuid") String uuid,
            @RequestParam int transTaskId,
            @RequestBody TaskNotifyFailedRequest request
    ) throws NotFoundException {
        Node node = nodeService.getOneNodeById(uuid);
        if (node == null) {
            throw new NotFoundException(uuid + " 노드를 찾을 수 없음");
        }
        node.setStatus("IDLE");
        TransTaskResult transTask = transTaskService.getTransTaskById(transTaskId);
        if (transTask == null) {
            throw new NotFoundException(transTaskId + " 번역 작업을 찾을 수 없음");
        }
        OCRResult ocrResult = ocrTaskService.getOCRResultById(transTask.getOcrResultId());
        OCRTask ocrTask = ocrTaskService.getOCRTaskById(ocrResult.getOcrTaskId());
        Task task = taskService.getTaskByIdAdmin(ocrTask.getTaskId());

        nodeService.updateOneNode(node);
        task.setStatus("failed");
        task.setFailCause(request.getError());
        taskService.updateTask(task);

        transTask.setStatus("failed");
        transTask.setFailCause(request.getError());
        transTaskService.updateTransTask(transTask);
        return new ResponseEntity<>(new GeneralResponse("Trans Failed Reported"), HttpStatus.OK);
    }

    @PostMapping("/recovery")
    public ResponseEntity<GeneralResponse> postTaskRecovery(@RequestBody TaskRecoveryRequest request) throws NotFoundException {
        int ocrResultId = request.getOcrResultId();
        OCRResult ocrResult = ocrTaskService.getOCRResultById(ocrResultId);
        if (ocrResult == null) {
            throw new NotFoundException(ocrResultId + " OCR 결과를 찾을 수 없음");
        }
        int transResultId = request.getTransResultId();
        TransTaskResult transTaskResult = transTaskService.getTransTaskById(transResultId);
        if (transTaskResult == null) {
            throw new NotFoundException(transResultId + " 번역 결과를 찾을 수 없음");
        }

        transTaskResult.setIsRecovery(1);
        transTaskService.updateTransTask(transTaskResult);

        TransTaskResult transTask = transTaskService.createTransTask(ocrResultId, request.getOriginalText(), request.getTranslateFrom(), request.getTranslateTo());
        transTaskService.createTransTaskMessage(transTask);

        return new ResponseEntity<>(new GeneralResponse("Recovery sent"), HttpStatus.OK);
    }

    @PostMapping("/recovery-list")
    public ResponseEntity<GeneralResponse> postTaskRecoveryList(@RequestBody TaskRecoveryRequest[] requests) throws NotFoundException {
        for (TaskRecoveryRequest request : requests) {
            int ocrResultId = request.getOcrResultId();
            OCRResult ocrResult = ocrTaskService.getOCRResultById(ocrResultId);
            if (ocrResult == null) {
                throw new NotFoundException(ocrResultId + " OCR 결과를 찾을 수 없음");
            }
            int transResultId = request.getTransResultId();
            TransTaskResult transTaskResult = transTaskService.getTransTaskById(transResultId);
            if (transTaskResult == null) {
                throw new NotFoundException(transResultId + " 번역 결과를 찾을 수 없음");
            }

            transTaskResult.setIsRecovery(1);
            transTaskService.updateTransTask(transTaskResult);

            TransTaskResult transTask = transTaskService.createTransTask(ocrResultId, request.getOriginalText(), request.getTranslateFrom(), request.getTranslateTo());
            transTaskService.createTransTaskMessage(transTask);
        }

        return new ResponseEntity<>(new GeneralResponse("Recovery sent"), HttpStatus.OK);
    }

    @PostMapping("/accept-ocr")
    public ResponseEntity<GeneralResponse> postAcceptOCR(
            @RequestHeader(name = "x-uuid") String uuid,
            @RequestParam int ocrTaskId
    ) throws NotFoundException {
        Node node = nodeService.getOneNodeById(uuid);
        if (node == null) {
            throw new NotFoundException(uuid + " 노드를 찾을 수 없음");
        }
        OCRTask ocrTask = ocrTaskService.getOCRTaskById(ocrTaskId);
        if (ocrTask == null) {
            throw new NotFoundException(ocrTaskId + " OCR 작업을 찾을 수 없음");
        }
        node.setStatus("ocr-processing");
        nodeService.updateOneNode(node);
        ocrTask.setStatus("accepted");
        ocrTask.setWorkingNodeId(uuid);
        ocrTaskService.updateOCRTask(ocrTask);
        return new ResponseEntity<>(new GeneralResponse("Node Accepted OCR Task"), HttpStatus.OK);
    }

    @PostMapping("/accept-trans")
    public ResponseEntity<GeneralResponse> postAcceptTrans(
            @RequestHeader(name = "x-uuid") String uuid,
            @RequestParam int transTaskId
    ) throws NotFoundException {
        Node node = nodeService.getOneNodeById(uuid);
        if (node == null) {
            throw new NotFoundException(uuid + " 노드를 찾을 수 없음");
        }
        TransTaskResult transTaskResult = transTaskService.getTransTaskById(transTaskId);
        if (transTaskResult == null) {
            throw new NotFoundException(transTaskId + " 번역 작업을 찾을 수 없음");
        }
        node.setStatus("trans-processing");
        nodeService.updateOneNode(node);
        transTaskResult.setStatus("accepted");
        transTaskResult.setWorkingNodeId(uuid);
        transTaskService.updateTransTask(transTaskResult);
        return new ResponseEntity<>(new GeneralResponse("Node Accepted Translation Task"), HttpStatus.OK);
    }
}
