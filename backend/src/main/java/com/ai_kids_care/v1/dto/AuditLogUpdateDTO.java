package com.ai_kids_care.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogUpdateDTO implements Serializable {
    private Long kindergartenId;
    private Long userId;
    private String action;
    private String resourceType;
    private Long resourceId;
    private String ip;
    private String userAgent;
}
