package org.researchandreview.projecttsbackend.dto;

import lombok.Data;

@Data
public class KeepaliveRequest {
    private String cpu;
    private Integer cpuUsage;
    private String gpu;
    private Integer gpuUsage;
    private Integer ram;
    private Integer ramUsage;
    private Integer vram;
    private Integer vramUsage;
    private Integer weight;
}
