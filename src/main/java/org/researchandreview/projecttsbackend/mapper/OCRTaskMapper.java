package org.researchandreview.projecttsbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.researchandreview.projecttsbackend.model.OCRTask;

@Mapper
public interface OCRTaskMapper {
    OCRTask findOneOCRTaskById(int id);

    void insertOneOCRTask(OCRTask ocrTask);

    void updateOneOCRTask(OCRTask ocrTask);

}
