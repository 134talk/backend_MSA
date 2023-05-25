package kr.co.talk.domain.user.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Team {
    @Id
    @GeneratedValue
    private Long id;

    private String teamName;

    private String teamCode;

}
