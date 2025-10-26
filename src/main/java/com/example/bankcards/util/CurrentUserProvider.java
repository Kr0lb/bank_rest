package com.example.bankcards.util;

import com.example.bankcards.exception.UnauthorizedException;
import com.example.bankcards.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserProvider {

    public static CustomUserDetails getCurrentUser() {
        CustomUserDetails customUserDetails;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        } else throw new UnauthorizedException("Пользователь не авторизован");
        return customUserDetails;
    }

}
