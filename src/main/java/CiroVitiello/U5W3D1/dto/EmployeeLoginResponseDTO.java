package CiroVitiello.U5W3D1.dto;

import jakarta.validation.constraints.NotEmpty;

public record EmployeeLoginResponseDTO(@NotEmpty(message = "Token is required!")
        String accessToken) {
}
