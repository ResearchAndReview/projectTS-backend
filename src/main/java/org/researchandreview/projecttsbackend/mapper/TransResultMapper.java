package org.researchandreview.projecttsbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.researchandreview.projecttsbackend.model.TransTaskResult;

import java.util.List;

@Mapper
public interface TransResultMapper {
    TransTaskResult findTransResultById(int id);

    List<TransTaskResult> findTransResultsByTaskId(int taskId);

    List<TransTaskResult> findTransResultsByOCRResultId(int ocrResultId);

    void insertOneTransTask(TransTaskResult transTaskResult);

    void updateOneTransTask(TransTaskResult transTaskResult);
}
