package org.researchandreview.projecttsbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.researchandreview.projecttsbackend.model.Image;

@Mapper
public interface ImageMapper {
    int insertOneImage(Image image);

    Image findImageByTaskId(long taskId);

}
