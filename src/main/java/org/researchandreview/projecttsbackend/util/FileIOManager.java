package org.researchandreview.projecttsbackend.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileIOManager {
    public static void saveMultipartFileToLocal(MultipartFile file) throws IOException {
        String uploadDir = System.getenv("TEMP_IMG_FOLDER");
        String fileName = file.getOriginalFilename();
        // long size = file.getSize();

        File dir = new File(uploadDir);
        if(!dir.exists()){
            boolean created = dir.mkdirs();
            if(!created) {
                throw new IOException("Directory couldn't be created: " + uploadDir);
            }
        }
        file.transferTo(new File(uploadDir + "/" + fileName));
    }
}
