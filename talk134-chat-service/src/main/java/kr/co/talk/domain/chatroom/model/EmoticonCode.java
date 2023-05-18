package kr.co.talk.domain.chatroom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmoticonCode {
    EMOTICON_TP1(1), 
    EMOTICON_TP2(2), 
    EMOTICON_TP3(3), 
    EMOTICON_TP4(4), 
    EMOTICON_TP5(5), 
    EMOTICON_TP6(6);

    private int code;
}
