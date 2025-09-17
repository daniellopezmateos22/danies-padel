// UserDto.java
package com.daniespadel.app.user.dto;

import java.util.UUID;

public record UserDto(
    UUID id,
    String name,
    String email,
    String role,
    Integer level
) {}
