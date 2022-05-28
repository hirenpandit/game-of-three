package com.takeaway.gameofthree.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class MoveDTO {

    int curr;
    int next;
    String operation;
    String playerId;

}
