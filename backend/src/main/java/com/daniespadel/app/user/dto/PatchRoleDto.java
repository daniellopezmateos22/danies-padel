// PatchRoleDto.java
package com.daniespadel.app.user.dto;

import jakarta.validation.constraints.NotBlank;

public record PatchRoleDto(@NotBlank String role) {}
