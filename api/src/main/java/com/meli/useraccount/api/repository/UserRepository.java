package com.meli.useraccount.api.repository;

import com.meli.useraccount.api.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
  List<UserModel> findByUsername(String username);
}
