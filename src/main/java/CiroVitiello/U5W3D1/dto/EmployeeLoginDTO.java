package CiroVitiello.U5W3D1.dto;

import jakarta.validation.constraints.NotEmpty;

public record EmployeeLoginDTO(@NotEmpty(message = "Email is required! ")
                           String email,
                               @NotEmpty(message = "Password is required!")
                           String password) {
}
