package com.example.bankcards.dto.request;

import com.example.bankcards.entity.Role;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UpdateUserByAdminRequest extends UpdateUserRequest {

    private Set<Role> roles;
    private Boolean enabled;

    @Email
    private String email;

}
