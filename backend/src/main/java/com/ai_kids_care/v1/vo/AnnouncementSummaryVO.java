package com.ai_kids_care.v1.vo;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * 공개 목록 API용 VO. 프론트 {@code AnnouncementSummary} 스키마와 필드명을 맞춘다.
 */
public record AnnouncementSummaryVO(
        Long id,
        String title,
        boolean pinned,
        Long viewCount,
        OffsetDateTime publishedAt,
        OffsetDateTime createdAt
) implements Serializable {
}
