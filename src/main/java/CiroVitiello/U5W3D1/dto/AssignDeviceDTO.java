package CiroVitiello.U5W3D1.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AssignDeviceDTO(
        @NotNull(message = " please insert the ID of the employee")
        @Min(value = 1, message = "the ID must be at least 1")
        long employeeId) {
}
