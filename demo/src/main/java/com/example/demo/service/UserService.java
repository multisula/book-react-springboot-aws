package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public UserEntity create(final UserEntity userEntity) {
    if(userEntity == null || userEntity.getEmail() == null) {
      throw new RuntimeException("Invalid arguments");
    }

    final String email = userEntity.getEmail();
    if(userRepository.existsByEmail(email)){
      log.warn("Email already exists {}", email);
      throw new RuntimeException("Email already exists");
    }

    return userRepository.save(userEntity);
  }

  public UserEntity getByCredentials(final String email, final String password){
    return userRepository.findByEmailAndPassword(email, password);
  }
}
