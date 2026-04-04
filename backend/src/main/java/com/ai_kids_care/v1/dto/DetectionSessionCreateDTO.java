package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link com.ai_kids_care.v1.entity.DetectionSession}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetectionSessionCreateDTO implements Serializable {
    private Long sessionId;
    private Long kindergartenId;
    private Long cameraId;
    private Long streamId;
    private Long modelId;
    private OffsetDateTime startedAt;
    private OffsetDateTime endedAt;
    private StatusEnum status;
    private Integer avgLatencyMs;
    private Double inferenceFps;
}