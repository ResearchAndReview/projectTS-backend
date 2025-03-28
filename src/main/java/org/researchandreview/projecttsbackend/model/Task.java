package org.researchandreview.projecttsbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Task {
    private final String name;
    private final String description;
    private String status = "NONE";
    private final Date date;

}
