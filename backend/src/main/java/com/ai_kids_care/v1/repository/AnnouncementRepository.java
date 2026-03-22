package com.ai_kids_care.v1.repository;

import com.ai_kids_care.v1.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    /** 수정·삭제 등 관리용: 소프트삭제되지 않은 행만 */
    Optional<Announcement> findByIdAndDeletedAtIsNull(Long id);
}
