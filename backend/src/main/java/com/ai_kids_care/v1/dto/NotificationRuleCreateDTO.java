package com.ai_kids_care.v1.dto;
import com.ai_kids_care.v1.type.EventTypeEnum;
import com.ai_kids_care.v1.type.NotificationTargetType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link com.ai_kids_care.v1.entity.NotificationRule}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRuleCreateDTO implements Serializable {
    private Long ruleId;
    @NotNull
    private Long kindergartenId;
    @NotNull
    private Long userId;
    @NotNull
    private NotificationTargetType targetType;
    @NotNull
    private Long targetId;
    private EventTypeEnum eventType;
    @NotNull
    @Min(0)
    private Integer minSeverity;
    private String quietHoursJson;
    @NotNull
    private Boolean enabled;
    private OffsetDateTime createdAt;
}