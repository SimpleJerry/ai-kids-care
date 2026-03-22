package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.NotificationTargetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRuleCreateDTO implements Serializable {
    private Long kindergartenId;
    private Long userId;
    private NotificationTargetType targetType;
    private Long targetId;
    private String eventType;
    private Integer minSeverity;
    private String quietHoursJson;
    private Boolean enabled;
}
