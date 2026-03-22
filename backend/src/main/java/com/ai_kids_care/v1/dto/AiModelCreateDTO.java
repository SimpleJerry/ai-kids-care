package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.ai_kids_care.v1.entity.AiModel}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiModelCreateDTO implements Serializable {
    private String name;
    private String version;
    private StatusEnum status;
}
