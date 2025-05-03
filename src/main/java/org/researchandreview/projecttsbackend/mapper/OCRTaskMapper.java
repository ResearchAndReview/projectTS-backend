package org.researchandreview.projecttsbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.researchandreview.projecttsbackend.model.OCRResult;
import org.researchandreview.projecttsbackend.model.OCRTask;
import org.researchandreview.projecttsbackend.model.ResultData;

import java.util.List;

@Mapper
public interface OCRTaskMapper {
    int insertOneOCRTask(OCRTask ocrTask);

}
