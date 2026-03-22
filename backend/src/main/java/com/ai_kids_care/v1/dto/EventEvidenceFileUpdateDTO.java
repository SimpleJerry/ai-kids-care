package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.EvidenceFileTypeEnum;
import com.ai_kids_care.v1.type.MimeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventEvidenceFileUpdateDTO implements Serializable {
    private Long eventId;
    private EvidenceFileTypeEnum type;
    private String storageUri;
    private MimeTypeEnum mimeType;
    private OffsetDateTime retentionUntil;
    private Boolean hold;
    private String hash;
}
