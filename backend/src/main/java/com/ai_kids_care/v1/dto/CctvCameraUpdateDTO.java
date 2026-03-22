package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CctvCameraUpdateDTO implements Serializable {
    private Long kindergartenId;
    private Long createdByUserId;
    private String serialNo;
    private String cameraName;
    private String model;
    private StatusEnum status;
    private OffsetDateTime lastSeenAt;
}
