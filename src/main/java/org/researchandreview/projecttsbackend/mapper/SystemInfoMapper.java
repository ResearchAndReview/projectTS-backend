package org.researchandreview.projecttsbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.researchandreview.projecttsbackend.model.Node;
import org.researchandreview.projecttsbackend.model.SystemInfo;

import java.util.List;

@Mapper
public interface SystemInfoMapper {

    List<SystemInfo> findAllSystemInfos();
    SystemInfo findOneSystemInfoByIdAdmin(int id);
    SystemInfo findOneSystemInfoByNodeIdAdmin(String nodeId);
    void insertSystemInfo(SystemInfo systemInfo);
    void updateSystemInfo(SystemInfo systemInfo);
}
