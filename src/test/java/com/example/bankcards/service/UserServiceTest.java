package com.example.bankcards.service;

import com.example.bankcards.dto.request.UpdateUserByAdminRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private User user;
    private UpdateUserByAdminRequest updateUserByAdminRequest;

    @BeforeEach
    public void setUp() {
        updateUserByAdminRequest = UpdateUserByAdminRequest.builder().email("ivan@example.com").build();
        user = User.builder().id(UUID.randomUUID()).email("ivanov@example.com").firstname("Ivan").build();
    }


    @Test
    void updateUserByAdmin_Success() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.updateUserByAdmin(user.getId(), updateUserByAdminRequest);

        verify(userRepository).save(user);
    }

    @Test
    void updateUserByAdmin_ThrowException() {
        assertThrows(UserNotFoundException.class, () ->
                userService.updateUserByAdmin(user.getId(), updateUserByAdminRequest));
    }

}
