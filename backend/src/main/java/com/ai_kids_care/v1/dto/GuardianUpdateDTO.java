package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.GenderEnum;
import com.ai_kids_care.v1.type.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuardianUpdateDTO implements Serializable {
    private Long kindergartenId;
    private Long userId;
    private String name;
    private String rrnEncrypted;
    private String rrnFirst6;
    private GenderEnum gender;
    private String address;
    private StatusEnum status;
}
