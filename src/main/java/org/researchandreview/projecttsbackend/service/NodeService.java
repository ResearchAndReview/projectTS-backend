package org.researchandreview.projecttsbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.researchandreview.projecttsbackend.mapper.NodeMapper;
import org.researchandreview.projecttsbackend.model.Node;
import org.researchandreview.projecttsbackend.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NodeService {

    private final NodeMapper nodeMapper;

    @Autowired
    public NodeService(NodeMapper nodeMapper) {
        this.nodeMapper = nodeMapper;
    }

    public String createOneNode(String ip) {
        Node newNode = new Node();
        String newUUID = UUIDGenerator.generateUUID();
        newNode.setId(newUUID);
        newNode.setId(ip);
        nodeMapper.insertOneNode(newNode);
        return newUUID;
    }
}
