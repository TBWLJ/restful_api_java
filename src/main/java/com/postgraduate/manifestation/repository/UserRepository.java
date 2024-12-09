package com.postgraduate.manifestation.repository;

import com.postgraduate.manifestation.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
}
