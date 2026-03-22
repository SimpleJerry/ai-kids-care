package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassUpdateDTO implements Serializable {
    private Long kindergartenId;
    private String name;
    private String grade;
    private Long academicYear;
    private LocalDate startDate;
    private LocalDate endDate;
    private StatusEnum status;
}
