package org.researchandreview.projecttsbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.researchandreview.projecttsbackend.model.OCRResult;
import org.researchandreview.projecttsbackend.model.ResultData;

import java.util.List;

@Mapper
public interface OCRResultMapper {
    List<OCRResult> findOCRResultsByTaskId(int taskId);

    List<OCRResult> findOCRResultsByOCRTaskId(int ocrTaskId);

    OCRResult findOCRResultById(int id);

    List<ResultData> findOCRResultsWithTransResultByTaskId(long taskId);
}
