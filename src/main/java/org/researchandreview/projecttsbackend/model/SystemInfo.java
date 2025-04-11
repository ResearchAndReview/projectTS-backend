package org.researchandreview.projecttsbackend.model;

import lombok.Data;

@Data
public class SystemInfo {
    private Integer id;
    private Integer nodeId;
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