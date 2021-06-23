package com.meli.useraccount.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "UserAccount")
@Entity
public class UserModel {
  @NotNull(message = "The ID field is required.")
  @JsonProperty("id")
  @Id
  @Column(name = "id")
  @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
  @GeneratedValue(generator = "UUIDGenerator")
  private UUID id;

  @NotBlank(message = "The username field is required.")
  @JsonProperty("username")
  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Size(min = 8, message = "The password field must have at least 8 characters.")
  @NotBlank(message = "The password field is required.")
  @JsonProperty("password")
  @Column(name = "password", nullable = false)
  private String password;

  @NotBlank(message = "The first name field is required.")
  @JsonProperty("first_name")
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @NotBlank(message = "The last name field is required.")
  @JsonProperty("last_name")
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @NotNull(message = "The birth date field is required.")
  @JsonProperty("birth_date")
  @Column(name = "birth_date", nullable = false)
  private LocalDate birthDate;
}
