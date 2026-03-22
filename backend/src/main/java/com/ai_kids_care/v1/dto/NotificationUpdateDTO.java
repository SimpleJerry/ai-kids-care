package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.NotificationChannelEnum;
import com.ai_kids_care.v1.type.NotificationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationUpdateDTO implements Serializable {
    private Long eventId;
    private Long recipientUserId;
    private NotificationChannelEnum channel;
    private String title;
    private String body;
    private NotificationStatusEnum status;
    private String dedupeKey;
    private OffsetDateTime sentAt;
    private String failReason;
    private Integer retryCount;
}
