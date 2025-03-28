package org.researchandreview.projecttsbackend.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class GeneralReponse {
    protected final String message;
    protected final Date timestamp = new Date();
}
