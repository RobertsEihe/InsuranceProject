package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }


    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }


    public Optional<Users> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<Users> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }


    public boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public Users updateUser(Users user) {
        if (userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found with id: " + user.getId());
    }
}