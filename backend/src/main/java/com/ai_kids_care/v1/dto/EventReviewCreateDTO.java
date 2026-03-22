package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventReviewCreateDTO implements Serializable {
    private Long eventId;
    private Long userId;
    private EventStatusEnum fromStatus;
    private EventStatusEnum resultStatus;
    private String comment;
}
