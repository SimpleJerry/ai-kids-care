package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.CameraStreamTypeEnum;
import com.ai_kids_care.v1.type.ProtocolEnum;
import com.ai_kids_care.v1.type.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link com.ai_kids_care.v1.entity.CameraStream}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraStreamCreateDTO implements Serializable {
    private Long streamId;
    private Long kindergartenId;
    private Long cameraId;
    private CameraStreamTypeEnum streamType;
    private String sourceUrl;
    private String streamUser;
    private String streamPassword;
    private ProtocolEnum sourceProtocol;
    private String playbackUrl;
    private ProtocolEnum playbackProtocol;
    private Integer fps;
    private String resolution;
    private Boolean isPrimary;
    private Boolean enabled;
    private StatusEnum status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
