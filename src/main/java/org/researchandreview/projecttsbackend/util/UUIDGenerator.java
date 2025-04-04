package org.researchandreview.projecttsbackend.util;

import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class UUIDGenerator {
    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }
}
