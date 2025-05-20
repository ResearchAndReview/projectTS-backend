package org.researchandreview.projecttsbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.researchandreview.projecttsbackend.dto.GeneralRequest;
import org.researchandreview.projecttsbackend.dto.GeneralResponse;
import org.researchandreview.projecttsbackend.dto.KeepaliveRequest;
import org.researchandreview.projecttsbackend.dto.NodeRegisterSuccessResponse;
import org.researchandreview.projecttsbackend.model.Node;
import org.researchandreview.projecttsbackend.model.SystemInfo;
import org.researchandreview.projecttsbackend.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/node")
public class NodeController {
    private final NodeService nodeService;

    @Autowired
    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @PostMapping("/keepalive")
    public ResponseEntity<GeneralResponse> keepalive(
            HttpServletRequest httpRequest,
            @RequestParam String nodeId,
            @RequestBody KeepaliveRequest request) {
        String ipAddress = httpRequest.getRemoteAddr();
        nodeService.updateOneNode(nodeId, ipAddress);
        nodeService.updateOneSystemInfo(nodeId, request);

        return new ResponseEntity<>(new GeneralResponse("OK"), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<NodeRegisterSuccessResponse> postNodeRegister(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        String newUUID = nodeService.createOneNode(ipAddress);
        nodeService.createOneSystemInfo(newUUID);

        return new ResponseEntity<>(new NodeRegisterSuccessResponse(newUUID), HttpStatus.OK);
    }

}
