package org.researchandreview.projecttsbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.researchandreview.projecttsbackend.model.Task;

import java.util.List;

@Mapper
public interface TaskMapper {
    List<Task> findAllTasks();

    void insertOneTask(Task task);

    Task findOneTaskById(int id, String uuid);

    Task findOneTaskByIdAdmin(int id);

    void updateOneTask(Task task);
}
