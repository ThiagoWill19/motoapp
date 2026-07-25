package com.will.motoapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.will.motoapp.models.dto.user.CreateUserDto;
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

    public void validateUniquenessEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
    }
}
