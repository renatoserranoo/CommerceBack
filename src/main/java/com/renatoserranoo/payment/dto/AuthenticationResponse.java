package com.renatoserranoo.payment.dto;

import com.renatoserranoo.payment.entity.UserRole;

public record AuthenticationResponse(String name, String token, UserRole role) {
}
