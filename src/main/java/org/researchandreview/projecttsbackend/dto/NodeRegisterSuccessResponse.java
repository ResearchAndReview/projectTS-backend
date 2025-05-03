package org.researchandreview.projecttsbackend.dto;

import lombok.Getter;

@Getter
public class NodeRegisterSuccessResponse extends GeneralResponse{
    private final String uuid;
    public NodeRegisterSuccessResponse(String uuid) {
        super("노드 UUID 발급됨");
        this.uuid = uuid;
    }
}
