package CiroVitiello.U5W3D1.dto;

import jakarta.validation.constraints.NotEmpty;

public record NewDeviceDTO(@NotEmpty(message = "Typology is required!")
                           String typology) {
}
