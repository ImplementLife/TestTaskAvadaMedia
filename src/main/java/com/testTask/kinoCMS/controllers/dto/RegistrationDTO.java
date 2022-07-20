package com.testTask.kinoCMS.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "RegistrationDTO")
public class RegistrationDTO {
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    public String username;
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    public String password;
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    public String confirmPassword;
}
