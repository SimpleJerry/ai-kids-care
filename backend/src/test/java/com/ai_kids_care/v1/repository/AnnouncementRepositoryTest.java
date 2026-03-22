package com.ai_kids_care.v1.repository;

import com.ai_kids_care.AiKidsCareApplication;
import com.ai_kids_care.v1.entity.Announcement;
import com.ai_kids_care.v1.entity.User;
import com.ai_kids_care.v1.type.StatusEnum;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AiKidsCareApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers(disabledWithoutDocker = true)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class AnnouncementRepositoryTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"))
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void datasourceProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void findByIdAndDeletedAtIsNull_returnsOnlyNonDeleted() {
        OffsetDateTime fixedNow = OffsetDateTime.parse("2026-06-15T12:00:00Z");

        User author = User.builder()
                .loginId("author-announce-test")
                .email("author-announce-test@example.com")
                .phone("01000000000")
                .passwordHash("hash")
                .status(StatusEnum.ACTIVE)
                .createdAt(fixedNow)
                .updatedAt(fixedNow)
                .build();
        entityManager.persist(author);
        entityManager.flush();

        Announcement active = new Announcement();
        active.setAuthor(author);
        active.setTitle("공지");
        active.setBody("본문");
        active.setIsPinned(false);
        active.setStatus(StatusEnum.ACTIVE);
        active.setViewCount(0L);
        active.setCreatedAt(fixedNow);
        active.setUpdatedAt(fixedNow);
        active.setDeletedAt(null);
        entityManager.persist(active);

        Announcement softDeleted = new Announcement();
        softDeleted.setAuthor(author);
        softDeleted.setTitle("삭제됨");
        softDeleted.setBody("본문");
        softDeleted.setIsPinned(false);
        softDeleted.setStatus(StatusEnum.ACTIVE);
        softDeleted.setViewCount(0L);
        softDeleted.setCreatedAt(fixedNow);
        softDeleted.setUpdatedAt(fixedNow);
        softDeleted.setDeletedAt(fixedNow);
        entityManager.persist(softDeleted);

        entityManager.flush();
        entityManager.clear();

        assertThat(announcementRepository.findByIdAndDeletedAtIsNull(active.getId())).isPresent();
        assertThat(announcementRepository.findByIdAndDeletedAtIsNull(softDeleted.getId())).isEmpty();
    }
}
