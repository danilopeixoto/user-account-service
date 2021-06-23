package com.meli.useraccount.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPartialRequestModel {
  @Pattern(regexp = "\\s*\\S.*", message = "The username field is required.")
  @JsonProperty("username")
  private String username;

  @Pattern(regexp = "\\s*\\S.*", message = "The password field is required.")
  @Size(min = 8, message = "The password field must have at least 8 characters.")
  @JsonProperty("password")
  private String password;

  @Pattern(regexp = "\\s*\\S.*", message = "The first name field is required.")
  @JsonProperty("first_name")
  private String firstName;

  @Pattern(regexp = "\\s*\\S.*", message = "The last name field is required.")
  @JsonProperty("last_name")
  private String lastName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @JsonProperty("birth_date")
  private LocalDate birthDate;
}
