package com.eliseo.user.repository;

import com.eliseo.user.domain.UserRegister;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserRegister, Long> {
}
