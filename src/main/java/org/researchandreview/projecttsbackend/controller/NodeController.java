package org.researchandreview.projecttsbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.researchandreview.projecttsbackend.dto.GeneralRequest;
import org.researchandreview.projecttsbackend.dto.GeneralResponse;
import org.researchandreview.projecttsbackend.dto.NodeRegisterResponse;
import org.researchandreview.projecttsbackend.dto.NodeRegisterSuccessResponse;
import org.researchandreview.projecttsbackend.util.UUIDGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/node")
public class NodeController {
    @PostMapping("/keepalive")
    public ResponseEntity<GeneralResponse> keepalive(
            @RequestHeader(name = "x-uuid") String uuid,
            @RequestBody GeneralRequest request) {
        return new ResponseEntity<>(new GeneralResponse("OK"), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<NodeRegisterSuccessResponse> PostNodeRegister(){
        String newUUID = UUIDGenerator.generateUUID();
        return new ResponseEntity<>(new NodeRegisterSuccessResponse(newUUID), HttpStatus.OK);
    }

}
