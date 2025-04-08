package org.researchandreview.projecttsbackend.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Image {
    private Integer id;
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
    private String imageUrl;
    private Timestamp createdAt;
    private Integer taskId;
}