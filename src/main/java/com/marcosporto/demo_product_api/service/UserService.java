package com.marcosporto.demo_product_api.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.marcosporto.demo_product_api.entity.User;
import com.marcosporto.demo_product_api.entity.User.Role;
import com.marcosporto.demo_product_api.exception.EntityNotFoundException;
import com.marcosporto.demo_product_api.exception.UsernameUniqueViolationExcepetion;
import com.marcosporto.demo_product_api.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User create(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationExcepetion(
                    String.format("Username '%s' já cadastrado", user.getUsername()));
        }

    }

    @Transactional
    public User searchByIdUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encotrado.", id)));
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Nova senha não confere com confirmação de senha.");
        }

        User user = searchByIdUser(id);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Sua senha não confere.");
        }
        user.setPassword(passwordEncoder.encode(newPassword));

        return user;
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User searchUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com '%s' não encotrado.", username)));
    }

    @Transactional
    public Role searchGetRoleUsername(String username) {
        return userRepository.findRoleByUsername(username);

    }

}
