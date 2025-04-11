package org.researchandreview.projecttsbackend.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileIOManager {
    public static void saveMultipartFileToLocal(MultipartFile file) throws IOException {
        String uploadDir = System.getenv("TEMP_IMG_FOLDER");
        //log.info("uploadDir: " + uploadDir);
        String fileName = file.getOriginalFilename();
        long size = file.getSize();
        //log.info("fileName: " + fileName);
        //log.info("size: " + size);

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new IOException("Directory couldn't be created: " + uploadDir);
            }
        }

        assert fileName != null;
        File newFile = new File(dir, fileName);

        file.transferTo(newFile.toPath());
    }
}
