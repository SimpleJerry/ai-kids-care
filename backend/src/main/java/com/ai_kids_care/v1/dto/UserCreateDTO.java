package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO implements Serializable {
    private String loginId;
    private String email;
    private String phone;
    private String passwordHash;
    private StatusEnum status;
    private OffsetDateTime lastLoginAt;
}
