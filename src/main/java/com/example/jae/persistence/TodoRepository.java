package com.example.jae.persistence;

import com.example.jae.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository                             // JpaRepository : 기본적인 데이터베이스 기능 인터페이스를 제공
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    // ?1은 메서드의 매개변수의 순서 위치
    @Query("select t from TodoEntity t where t.userId = ?1")
    List<TodoEntity> findByUserId(String userId);
}
