package com.ai_kids_care.v1.controller;

import com.ai_kids_care.v1.dto.RegisterFieldAvailabilityResponse;
import com.ai_kids_care.v1.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * 회원가입 시 로그인 ID·이메일·연락처 중복 확인 — {@code SecurityConfig} 의 {@code /api/v1/auth/**} permitAll.
 */
@RestController
@RequestMapping("${openapi.aIKidsCare.base-path:/api}")
@RequiredArgsConstructor
public class AuthRegisterAvailabilityController {

    private final AuthService authService;

    /**
     * GET /api/v1/auth/register/availability?field=loginId|email|phone&amp;value=...
     */
    @GetMapping("/v1/auth/register/availability")
    public RegisterFieldAvailabilityResponse registerFieldAvailability(
            @RequestParam("field") String field,
            @RequestParam(value = "value", required = false, defaultValue = "") String value
    ) {
        try {
            return authService.checkRegisterFieldAvailability(field, value);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
