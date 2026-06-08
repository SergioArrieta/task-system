package com.zerst.taskSystem.serviceTasks.repository;

import com.zerst.taskSystem.serviceTasks.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM Task t WHERE t.userId = :userId")
    int deleteByUserId(@Param("userId") Long userId);

}