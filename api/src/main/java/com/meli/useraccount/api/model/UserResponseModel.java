package com.meli.useraccount.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseModel {
  @NotNull(message = "The ID field is required.")
  @JsonProperty("id")
  private UUID id;

  @NotBlank(message = "The username field is required.")
  @JsonProperty("username")
  private String username;

  @NotBlank(message = "The first name field is required.")
  @JsonProperty("first_name")
  private String firstName;

  @NotBlank(message = "The last name field is required.")
  @JsonProperty("last_name")
  private String lastName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @NotNull(message = "The birth date field is required.")
  @JsonProperty("birth_date")
  private LocalDate birthDate;
}
