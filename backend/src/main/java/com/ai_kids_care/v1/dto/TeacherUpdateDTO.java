package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.GenderEnum;
import com.ai_kids_care.v1.type.LevelEnum;
import com.ai_kids_care.v1.type.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherUpdateDTO implements Serializable {
    private Long kindergartenId;
    private Long userId;
    private String staffNo;
    private String name;
    private GenderEnum gender;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String rrnEncrypted;
    private String rrnFirst6;
    private LevelEnum level;
    private LocalDate startDate;
    private LocalDate endDate;
    private StatusEnum status;
}
