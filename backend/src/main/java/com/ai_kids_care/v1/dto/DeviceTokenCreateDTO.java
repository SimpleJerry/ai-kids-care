package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.DevicePlatformEnum;
import com.ai_kids_care.v1.type.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link com.ai_kids_care.v1.entity.DeviceToken}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceTokenCreateDTO implements Serializable {
    private Long deviceId;
    @NotNull
    private Long userId;
    @NotNull
    private DevicePlatformEnum platform;
    @NotBlank
    private String pushToken;
    private StatusEnum status;
    private OffsetDateTime lastSeenAt;
    private OffsetDateTime createdAt;
}