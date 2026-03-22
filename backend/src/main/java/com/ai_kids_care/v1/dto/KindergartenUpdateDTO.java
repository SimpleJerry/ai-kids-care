package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KindergartenUpdateDTO implements Serializable {
    private String name;
    private String address;
    private String regionCode;
    private String code;
    private String businessRegistrationNo;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private StatusEnum status;
}
