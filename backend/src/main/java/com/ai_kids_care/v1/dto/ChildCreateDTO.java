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
public class ChildCreateDTO implements Serializable {
    private Long kindergartenId;
    private String name;
    private String childNo;
    private String rrnEncrypted;
    private String rrnFirst6;
    private LocalDate birthDate;
    private String gender;
    private String address;
    private LocalDate enrollDate;
    private LocalDate leaveDate;
    private StatusEnum status;
}
