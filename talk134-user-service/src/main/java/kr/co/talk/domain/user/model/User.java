package kr.co.talk.domain.user.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long userId;

    //소셜 로그인시 유저 정보키
    @Column(nullable = false)
    private String userUid;

    private String userName;

    @Column(length = 20)
    private String nickname;

    private String role;

    private String teamCode;

    private int statusEnergy;

    private int statusRelation;

    private int statusStress;

    private String statusKeyword;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createTime;

    private Timestamp lastLoginTime;


}