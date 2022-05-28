package com.takeaway.gameofthree.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlayerDTO {

    String playerId;

    boolean isSuccess;

    int  count;

}
