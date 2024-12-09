package com.postgraduate.manifestation.services;
import com.postgraduate.manifestation.dto.UserDTO;
import com.postgraduate.manifestation.model.User;
import com.postgraduate.manifestation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public User createUser(UserDTO userDTO) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(userDTO.getEmail()));
        
        if (mongoTemplate.exists(query, User.class)) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(String id, UserDTO userDTO) {
        User user = getUserById(id);
        
        // Check if new email already exists for another user
        if (!user.getEmail().equals(userDTO.getEmail())) {
            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(userDTO.getEmail()));
            if (mongoTemplate.exists(query, User.class)) {
                throw new RuntimeException("Email already exists");
            }
        }
        
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}

