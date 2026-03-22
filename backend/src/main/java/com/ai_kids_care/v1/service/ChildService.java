package com.ai_kids_care.v1.service;

import com.ai_kids_care.v1.entity.Child;
import com.ai_kids_care.v1.repository.ChildRepository;
import com.ai_kids_care.v1.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ChildService {
    private final ChildRepository childRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private static final Pattern RRN_KEYWORD_PATTERN = Pattern.compile("^(\\d{6})-(\\d{7})$");

    public Page<Child> listChildren(String keyword, Integer page, Integer size, String sort) {
        int safePage = page == null || page < 0 ? 0 : page;
        int safeSize = size == null || size <= 0 ? 20 : size;
        String safeSort = (sort == null || sort.isBlank()) ? "createdAt" : sort;
        Pageable pageable = PageRequest.of(safePage, safeSize, Sort.by(Sort.Direction.DESC, safeSort));

        if (keyword == null || keyword.isBlank()) {
            return childRepository.findAll(pageable);
        }

        String trimmed = keyword.trim();
        Matcher rrnMatcher = RRN_KEYWORD_PATTERN.matcher(trimmed);
        if (rrnMatcher.matches()) {
            String rrnFirst6 = rrnMatcher.group(1);
            String rrnBack7 = rrnMatcher.group(2);
            return listChildrenByRrnParts(rrnFirst6, rrnBack7, pageable);
        }

        return childRepository.findByNameContains(trimmed, pageable);
    }

    public Page<Child> listChildrenByRrnParts(String rrnFirst6, String rrnBack7, Pageable pageable) {
        List<Child> candidates = childRepository.findByRrnFirst6(rrnFirst6);
        List<Child> matched = candidates.stream()
                .filter(child -> isRrnBack7Matched(child.getRrnEncrypted(), rrnBack7))
                .toList();
        return new org.springframework.data.domain.PageImpl<>(matched, pageable, matched.size());
    }

    private boolean isRrnBack7Matched(String rrnEncrypted, String rrnBack7) {
        if (rrnEncrypted == null || rrnEncrypted.isBlank()) {
            return false;
        }

        // Legacy bcrypt data
        try {
            if (passwordEncoder.matches(rrnBack7, rrnEncrypted)) {
                return true;
            }
        } catch (Exception ignored) {
            // continue with JWT matching
        }

        // JWT seed data: subject stores rrnBack7
        try {
            String subject = jwtUtil.extractIdentifier(rrnEncrypted);
            return rrnBack7.equals(subject);
        } catch (Exception ignored) {
            return false;
        }
    }
}
