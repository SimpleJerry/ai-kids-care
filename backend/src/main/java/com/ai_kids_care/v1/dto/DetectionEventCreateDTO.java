package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.EventStatusEnum;
import com.ai_kids_care.v1.type.EventTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetectionEventCreateDTO implements Serializable {
    private Long cameraId;
    private Long roomId;
    private Long sessionId;
    private EventTypeEnum eventType;
    private Integer severity;
    private Double confidence;
    private OffsetDateTime detectedAt;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private EventStatusEnum status;
}
