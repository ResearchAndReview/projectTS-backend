package org.researchandreview.projecttsbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.researchandreview.projecttsbackend.model.Node;

import java.util.List;

@Mapper
public interface NodeMapper {
    List<Node> findAllNodes();

    void insertOneNode(Node node);

    Node findOneNodeByIdAdmin(String id);

    void updateOneNode(Node node);
}
