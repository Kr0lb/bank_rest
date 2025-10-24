package com.example.bankcards.dto.request;

import com.example.bankcards.entity.Role;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@SuperBuilder
@NoArgsConstructor
public class UpdateUserByAdminRequest extends UpdateUserRequest {

    private Set<Role> roles;
    private Boolean enabled;

    @Email
    private String email;

}
