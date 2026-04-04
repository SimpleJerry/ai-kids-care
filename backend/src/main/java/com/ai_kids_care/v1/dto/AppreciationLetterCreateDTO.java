package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.AppreciationTargetTypeEnum;
import com.ai_kids_care.v1.type.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link com.ai_kids_care.v1.entity.AppreciationLetter}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppreciationLetterCreateDTO implements Serializable {
    private Long letterId;
    private Long kindergartenId;
    private Long senderUserId;
    private AppreciationTargetTypeEnum targetType;
    private Long targetId;
    private String title;
    private String content;
    private Boolean isPublic;
    private StatusEnum status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}