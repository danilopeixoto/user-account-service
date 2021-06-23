package com.meli.useraccount.api.service;

import com.meli.useraccount.api.model.UserModel;
import com.meli.useraccount.api.model.UserPartialRequestModel;
import com.meli.useraccount.api.model.UserRequestModel;
import com.meli.useraccount.api.model.UserResponseModel;
import com.meli.useraccount.api.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserService {
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private UserRepository repository;

  public Mono<UserResponseModel> create(Mono<UserRequestModel> userRequest) {
    return userRequest
      .map(user -> this.modelMapper.map(user, UserModel.class))
      .map(this.repository::save)
      .onErrorMap(
        DataIntegrityViolationException.class,
        (exception) -> new IllegalStateException("Account username already exists."))
      .map(user -> this.modelMapper.map(user, UserResponseModel.class));
  }

  public Mono<UserResponseModel> findById(UUID id) {
    return Mono
      .justOrEmpty(this.repository.findById(id))
      .map(user -> this.modelMapper.map(user, UserResponseModel.class))
      .switchIfEmpty(Mono.error(new NoSuchElementException("User account resource not found.")));
  }

  public Flux<UserResponseModel> findByUsername(String username) {
    return Flux
      .fromIterable(this.repository.findByUsername(username))
      .map(user -> this.modelMapper.map(user, UserResponseModel.class));
  }

  public Flux<UserResponseModel> list(Pageable pageable) {
    return Mono
      .fromCallable(() -> this.repository.findAll(pageable))
      .onErrorMap(
        PropertyReferenceException.class,
        (exception) -> new IllegalArgumentException("Invalid sort parameter."))
      .flatMapMany(Flux::fromIterable)
      .map(user -> this.modelMapper.map(user, UserResponseModel.class));
  }

  public Mono<UserResponseModel> update(UUID id, Mono<UserRequestModel> userRequest) {
    return Mono
      .justOrEmpty(this.repository.findById(id))
      .switchIfEmpty(Mono.error(new NoSuchElementException("User account resource not found.")))
      .flatMap(user -> userRequest
        .map(userUpdate -> this.modelMapper.map(userUpdate, UserModel.class))
        .map(userUpdate -> userUpdate
          .toBuilder()
          .id(user.getId())
          .build()))
      .map(this.repository::save)
      .onErrorMap(
        DataIntegrityViolationException.class,
        (exception) -> new IllegalStateException("Account username already exists."))
      .map(user -> this.modelMapper.map(user, UserResponseModel.class));
  }

  public Mono<UserResponseModel> partialUpdate(UUID id, Mono<UserPartialRequestModel> studentRequest) {
    return Mono
      .justOrEmpty(this.repository.findById(id))
      .switchIfEmpty(Mono.error(new NoSuchElementException("User account resource not found.")))
      .flatMap(student -> studentRequest
        .doOnNext(studentUpdate -> this.modelMapper.map(studentUpdate, student))
        .thenReturn(student))
      .map(this.repository::save)
      .onErrorMap(
        DataIntegrityViolationException.class,
        (exception) -> new IllegalStateException("Account username already exists."))
      .map(user -> this.modelMapper.map(user, UserResponseModel.class));
  }

  public Mono<UserResponseModel> delete(UUID id) {
    return Mono
      .justOrEmpty(this.repository.findById(id))
      .switchIfEmpty(Mono.error(new NoSuchElementException("User account resource not found.")))
      .map(user -> {
        this.repository.deleteById(user.getId());
        return user;
      })
      .map(user -> this.modelMapper.map(user, UserResponseModel.class));
  }
}
