package com.project.InsuranceProject.data.services;
import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRetrievalService {

    @Autowired
        private UserRepository userRepository;

        public Long getUserIdByUsername(String username) {
            Optional<Users> userOptional = userRepository.findByUsername(username);
            return userOptional.map(Users::getId).orElse(null);
    }
}
