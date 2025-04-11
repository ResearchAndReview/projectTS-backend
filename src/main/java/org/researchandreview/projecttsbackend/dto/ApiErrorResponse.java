package org.researchandreview.projecttsbackend.dto;

import lombok.Getter;

@Getter
public class ApiErrorResponse extends GeneralResponse {
    private final String errorCause;

    public ApiErrorResponse(String message, String errorCause) {
        super(message);
        this.errorCause = errorCause;
    }
}
