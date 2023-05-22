package kr.co.talk.domain.user.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    //소셜 로그인시 유저 정보키
    @Column(nullable = false)
    private String userUid;

    private String userName;

    @Column(length = 20)
    private String nickname;

    private String role;

    private int statusEnergy;

    private int statusRelation;

    private int statusStress;

    private String statusKeyword;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createTime;

    private Timestamp lastLoginTime;

    public void registerInfo(String userName, Team team, String role) {
        this.userName = userName;
        this.team = team;
        this.role = role;
    }

}