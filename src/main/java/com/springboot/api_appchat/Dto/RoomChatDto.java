package com.springboot.api_appchat.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomChatDto {
    private String roomName;
    private String passwordRoom;
    private long hostId;
}
