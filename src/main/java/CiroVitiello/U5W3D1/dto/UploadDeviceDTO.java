package CiroVitiello.U5W3D1.dto;

import jakarta.validation.constraints.NotEmpty;

public record UploadDeviceDTO(@NotEmpty(message = " Status is required!")
                              String status) {
}
