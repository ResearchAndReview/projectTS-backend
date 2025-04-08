package org.researchandreview.projecttsbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.researchandreview.projecttsbackend.dto.GeneralReponse;
import org.researchandreview.projecttsbackend.dto.GeneralRequest;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/node")
public class NodeController {


}
