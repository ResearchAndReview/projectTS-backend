package org.researchandreview.projecttsbackend.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class GeneralResponse {
    private final String message;
    private final Date timestamp = new Date();

    public GeneralResponse(String message) {
        this.message = message;
    }
}
