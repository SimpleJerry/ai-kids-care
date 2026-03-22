package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.CameraStreamTypeEnum;
import com.ai_kids_care.v1.type.ProtocolEnum;
import com.ai_kids_care.v1.type.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraStreamUpdateDTO implements Serializable {
    private Long cameraId;
    private CameraStreamTypeEnum streamType;
    private String streamUrl;
    private String streamUser;
    private String streamPasswordEncrypted;
    private ProtocolEnum protocol;
    private Integer fps;
    private String resolution;
    private Boolean isPrimary;
    private Boolean enabled;
    private StatusEnum status;
}
