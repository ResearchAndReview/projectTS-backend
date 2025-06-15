package org.researchandreview.projecttsbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.researchandreview.projecttsbackend.dto.KeepaliveRequest;
import org.researchandreview.projecttsbackend.mapper.NodeMapper;
import org.researchandreview.projecttsbackend.mapper.SystemInfoMapper;
import org.researchandreview.projecttsbackend.model.Node;
import org.researchandreview.projecttsbackend.model.SystemInfo;
import org.researchandreview.projecttsbackend.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NodeService {

    private final NodeMapper nodeMapper;
    private final SystemInfoMapper systemInfoMapper;

    @Autowired
    public NodeService(NodeMapper nodeMapper, SystemInfoMapper systemInfoMapper) {
        this.nodeMapper = nodeMapper;
        this.systemInfoMapper = systemInfoMapper;
    }

    public Node getOneNodeById(String nodeId) {
        return nodeMapper.findOneNodeByIdAdmin(nodeId);
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
    public SystemInfo createOneSystemInfo(String nodeId) {
        SystemInfo newSystemInfo = new SystemInfo();
        newSystemInfo.setNodeId(nodeId);

        systemInfoMapper.insertOneSystemInfo(newSystemInfo);

        return newSystemInfo;
    }
    
    public void updateOneNode(Node node) {
        nodeMapper.updateOneNode(node);
    }

    public void updateOneNode(String nodeId, String ip) {
        Node node = nodeMapper.findOneNodeByIdAdmin(nodeId);
        node.setIp(ip);
        nodeMapper.updateOneNode(node);
    }

    public void updateOneSystemInfo(String nodeId, KeepaliveRequest request) {
        SystemInfo systemInfo = systemInfoMapper.findOneSystemInfoByNodeIdAdmin(nodeId);
        systemInfo.setCpu(request.getCpu());
        systemInfo.setCpuUsage(request.getCpuUsage());
        systemInfo.setGpu(request.getGpu());
        systemInfo.setGpuUsage(request.getGpuUsage());
        systemInfo.setRam(request.getRam());
        systemInfo.setRamUsage(request.getRamUsage());
        systemInfo.setVram(request.getVram());
        systemInfo.setVramUsage(request.getVramUsage());

        // TODO: insert weight calc algorithm here
        // systemInfo.setWeight(weight);
        systemInfoMapper.updateOneSystemInfo(systemInfo);
    }
}
