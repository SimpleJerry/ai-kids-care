package com.ai_kids_care.v1.dto;

import com.ai_kids_care.v1.type.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomUpdateDTO implements Serializable {
    private Long kindergartenId;
    private String name;
    private String roomCode;
    private String locationNote;
    private String roomType;
    private StatusEnum status;
}
