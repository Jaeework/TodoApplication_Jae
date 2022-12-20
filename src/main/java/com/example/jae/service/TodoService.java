package com.example.jae.service;

import com.example.jae.model.TodoEntity;
import com.example.jae.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public String testService(){
        // TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        // TodoEntity 저장
        repository.save(entity);
        // TodoEntity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();

        //return "Test Service";
        return savedEntity.getTitle();

    }

    public List<TodoEntity> create(final TodoEntity entity) {
        //validations
        /*if(entity == null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknow user.");
            throw new RuntimeException("Unknown user.");
        } => validate method로 리팩토링*/

        validate(entity);

        repository.save(entity);
        log.info("Entity Id : {} is saved.", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity> read(final TodoEntity entity) {
        validate(entity);

        log.info("User Id : {} 's todo list returned.", entity.getUserId());
        return repository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity> update(TodoEntity entity) {

        validate(entity);

        repository.save(entity);
        log.info("Entity Id : {} was updated.", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    // 리팩토링한 메서드
    private void validate(final TodoEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }


    public String delete(TodoEntity entity) {

        if(!repository.existsById(entity.getId())) {
            log.warn("Entity Id {} does not exist.", entity.getId());
            throw new RuntimeException("Entity does not exist.");
        }

        //validate(entity);

        try {
            repository.deleteById(entity.getId());

            log.info("Entity Id : {} was deleted.", entity.getId());
            String message = String.format("Entity Id %s was deleted successfully", entity.getId());
            return message;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete the entity.");
        }
        //return repository.findByUserId(entity.getUserId());
    }
}
