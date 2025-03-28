package org.researchandreview.projecttsbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.researchandreview.projecttsbackend.dto.GeneralReponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {
    @GetMapping("/create")
    public ResponseEntity<GeneralReponse> taskCreateFromUser() {
        GeneralReponse generalReponse = new GeneralReponse("LLALD");
        log.info(generalReponse.getMessage());
        return new ResponseEntity<>(generalReponse, HttpStatus.OK);
    }
}
