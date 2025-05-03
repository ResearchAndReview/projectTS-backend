package org.researchandreview.projecttsbackend.model;

import lombok.Data;

@Data
public class Caption {
    private Integer height;
    private Integer width;
    private Integer x;
    private Integer y;
    private String text;




    /*"captions": [
    {
        "height": 30,
            "text": "さび",
            "width": 16,
            "x": 267,
            "y": 75
    },*/
}
