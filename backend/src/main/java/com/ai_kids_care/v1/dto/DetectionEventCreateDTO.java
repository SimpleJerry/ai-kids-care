package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.EventStatusEnum;
import com.ai_kids_care.v1.type.EventTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link com.ai_kids_care.v1.entity.DetectionEvent}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetectionEventCreateDTO implements Serializable {
    private Long eventId;
    @NotNull
    private Long kindergartenId;
    @NotNull
    private Long cameraId;
    @NotNull
    private Long roomId;
    @NotNull
    private Long sessionId;
    @NotNull
    private EventTypeEnum eventType;
    @NotNull
    @Min(0)
    private Integer severity;
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double confidence;
    @NotNull
    private OffsetDateTime detectedAt;
    @NotNull
    private OffsetDateTime startTime;
    @NotNull
    private OffsetDateTime endTime;
    @NotNull
    private EventStatusEnum status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}