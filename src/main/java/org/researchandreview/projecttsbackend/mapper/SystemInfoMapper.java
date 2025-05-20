package org.researchandreview.projecttsbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.researchandreview.projecttsbackend.model.Node;
import org.researchandreview.projecttsbackend.model.SystemInfo;

import java.util.List;

@Mapper
public interface SystemInfoMapper {

    List<SystemInfo> getAllSystemInfo();
    SystemInfo getSystemInfoById(int id);
    SystemInfo getSystemInfoByNodeId(String nodeId);
    void insertSystemInfo(SystemInfo systemInfo);
    void updateSystemInfo(SystemInfo systemInfo);
}
