package com.will.motoapp.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.will.motoapp.exceptions.ResourceNotFoundException;
import com.will.motoapp.models.dto.user.CreateUserDto;
import com.will.motoapp.models.dto.user.ResponseUserDto;
import com.will.motoapp.models.entity.User;
import com.will.motoapp.models.enums.Profile;
import com.will.motoapp.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public void create(CreateUserDto createUserDto) {

        validateUniquenessEmail(createUserDto.getEmail());

        createUserDto.setPassword(createUserDto.getPassword().trim());
        createUserDto.setConfirmPassword(createUserDto.getConfirmPassword().trim());

        if (!createUserDto.getPassword().equals(createUserDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Senhas não conferem");
        }
        
        String encodedPassword = passwordEncoder.encode(createUserDto.getPassword());

        User user = User.builder()
                .name(createUserDto.getName())
                .email(createUserDto.getEmail())
                .password(encodedPassword)
                .active(true)
                .profile(Profile.USER)
                .build();

        userRepository.save(user);
    }

    public ResponseUserDto getUserById(UUID userId) {
        
        User user = validateUser(userId);

        return ResponseUserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .profile(user.getProfile().name())
                .active(user.isActive())
                .build();
    }

    private void validateUniquenessEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
    }

    private User validateUser(UUID userId) {

        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }

        if(userOptional.get().isActive() == false) {
            throw new IllegalArgumentException("Usuário inativo");
        }

        return userOptional.get();

    }
}
