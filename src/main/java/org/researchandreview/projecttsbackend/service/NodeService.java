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
        newNode.setIp(ip);
        nodeMapper.insertOneNode(newNode);
        return newUUID;
    }

    public void decreaseOcrTaskSize(String id, Double ocrTaskSize) {
        Node node = nodeMapper.findOneNodeByIdAdmin(id);
        node.setOcrTaskSize( node.getOcrTaskSize() - ocrTaskSize );
    }

    public void setOcrPerf(String id, Double ocrPerf) {
        Node node = nodeMapper.findOneNodeByIdAdmin(id);
        node.setOcrPerf(ocrPerf);
    }

    public Double getOcrPerf(String id) {
        Node node = nodeMapper.findOneNodeByIdAdmin(id);
        return node.getOcrPerf();
    }

    public void decreaseTransTaskSize(String id, Double transTaskSize) {
        Node node = nodeMapper.findOneNodeByIdAdmin(id);
        node.setTransTaskSize( node.getTransTaskSize() - transTaskSize );
    }

    public void setTransPerf(String id, Double transPerf) {
        Node node = nodeMapper.findOneNodeByIdAdmin(id);
        node.setTransPerf(transPerf);
    }

    public Double getTransPerf(String id) {
        Node node = nodeMapper.findOneNodeByIdAdmin(id);
        return node.getTransPerf();
    }
}
