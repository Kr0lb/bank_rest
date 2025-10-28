package com.example.bankcards.service;

import com.example.bankcards.dto.request.UpdateUserByAdminRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void updateUserByAdmin(UUID userId, UpdateUserByAdminRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Пользователь '%s' не найден".formatted(userId)));
        userMapper.updateUSer(user, request);

        userRepository.save(user);
    }

}
