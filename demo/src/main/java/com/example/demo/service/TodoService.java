package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

  private final TodoRepository repository;

  public String testService() {
    TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
    repository.save(entity);
    TodoEntity savedEntity = repository.findById(entity.getId()).get();
    return savedEntity.getTitle();
  }

  public List<TodoEntity> create(final TodoEntity entity) {
    // Validations
    validate(entity);

    repository.save(entity);

    log.info("Entity Id : {} is saved.", entity.getId());

    return repository.findByUserId(entity.getUserId());
  }

  public List<TodoEntity> retrieve(final String userId) {
    return repository.findByUserId(userId);
  }

  private void validate(final TodoEntity entity) {
    if(entity == null) {
      log.warn("Entity cannot be null.");
      throw new RuntimeException("Entity cannot be null.");
    }

    if(entity.getUserId() == null) {
      log.warn("Unknown user.");
      throw new RuntimeException("Unknown user.");
    }
  }
}
