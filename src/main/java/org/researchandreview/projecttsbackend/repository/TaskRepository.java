package org.researchandreview.projecttsbackend.repository;

import org.apache.ibatis.session.SqlSession;
import org.researchandreview.projecttsbackend.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository {
    private final SqlSession sqlSession;

    @Autowired
    public TaskRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Task> getAllTasks() {
        return sqlSession.selectList("findAll");
    }
}
