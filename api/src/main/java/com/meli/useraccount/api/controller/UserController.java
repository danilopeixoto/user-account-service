package com.meli.useraccount.api.controller;

import com.meli.useraccount.api.model.ErrorResponseModel;
import com.meli.useraccount.api.model.UserPartialRequestModel;
import com.meli.useraccount.api.model.UserRequestModel;
import com.meli.useraccount.api.model.UserResponseModel;
import com.meli.useraccount.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@Tag(name = "User accounts")
@Validated
@RequestMapping("/accounts")
@RestController
public class UserController {
  @Autowired
  private UserService service;

  @Operation(summary = "Create user account", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = UserResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "409",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json"))
  })
  @PostMapping
  public Mono<UserResponseModel> create(@Valid @RequestBody Mono<UserRequestModel> userRequest) {
    return this.service.create(userRequest);
  }

  @Operation(summary = "Find user account by ID", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = UserResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "404",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json"))
  })
  @GetMapping("/{id}")
  public Mono<UserResponseModel> findById(@Valid @PathVariable UUID id) {
    return this.service.findById(id);
  }

  @Operation(summary = "List or find user account by name", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        array = @ArraySchema(schema = @Schema(implementation = UserResponseModel.class)),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json"))
  })
  @GetMapping("/")
  public Flux<UserResponseModel> listOrFindByUsername(
    @Valid @RequestParam(value = "username", required = false) String username,
    @Valid @RequestParam(value = "sort", defaultValue = "id") String sort,
    @Valid @RequestParam(value = "order", defaultValue = "ASC") Sort.Direction order,
    @Valid @RequestParam(value = "page", defaultValue = "0") Integer page,
    @Valid @RequestParam(value = "size", defaultValue = "100") Integer size) {
    return username != null ?
      this.service.findByUsername(username) :
      this.service.list(PageRequest.of(page, size, order, sort));
  }

  @Operation(summary = "Update user account by ID", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = UserResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "404",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "409",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json"))
  })
  @PutMapping("/{id}")
  public Mono<UserResponseModel> update(
    @Valid @PathVariable UUID id,
    @Valid @RequestBody Mono<UserRequestModel> userRequest) {
    return this.service.update(id, userRequest);
  }

  @Operation(summary = "Partially update user account by ID", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = UserResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "404",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "409",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json"))
  })
  @PatchMapping("/{id}")
  public Mono<UserResponseModel> partialUpdate(
    @Valid @PathVariable UUID id,
    @Valid @RequestBody Mono<UserPartialRequestModel> userRequest) {
    return this.service.partialUpdate(id, userRequest);
  }

  @Operation(summary = "Delete user account by ID", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = UserResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "404",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json"))
  })
  @DeleteMapping("/{id}")
  public Mono<UserResponseModel> delete(@Valid @PathVariable UUID id) {
    return this.service.delete(id);
  }
}
