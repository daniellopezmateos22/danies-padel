// PatchLevelDto.java
package com.daniespadel.app.user.dto;

import jakarta.validation.constraints.Min;

public record PatchLevelDto(@Min(0) Integer level) {}
