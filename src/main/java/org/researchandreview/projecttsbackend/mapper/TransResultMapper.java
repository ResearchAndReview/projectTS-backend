package org.researchandreview.projecttsbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.researchandreview.projecttsbackend.model.Task;
import org.researchandreview.projecttsbackend.model.TransTaskResult;

import java.util.List;

@Mapper
public interface TransResultMapper {
    TransTaskResult findTransResultById(long id);
    List<TransTaskResult> findTransResultsByTaskId(long taskId);
    List<TransTaskResult> findTransResultsByOCRResultId(long ocrResultId);
}
