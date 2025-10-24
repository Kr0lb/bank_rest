package com.example.bankcards.service;

import com.example.bankcards.dto.request.UpdateUserByAdminRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    public void updateUserByAdmin(UUID userId, UpdateUserByAdminRequest request) {

    }

}
