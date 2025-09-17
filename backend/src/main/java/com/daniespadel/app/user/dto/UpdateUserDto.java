// UpdateUserDto.java
package com.daniespadel.app.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserDto(
    @NotBlank String name,
    @Email @NotBlank String email,
    @Min(0) Integer level
) {}
