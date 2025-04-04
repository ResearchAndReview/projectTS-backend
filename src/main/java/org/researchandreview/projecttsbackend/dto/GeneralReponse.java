package org.researchandreview.projecttsbackend.dto;

import lombok.*;

import java.util.Date;

@Getter
public class GeneralReponse {
    protected final String message;
    protected final Date timestamp = new Date();

    public GeneralReponse(String message) {
        this.message = message;
    }
}
