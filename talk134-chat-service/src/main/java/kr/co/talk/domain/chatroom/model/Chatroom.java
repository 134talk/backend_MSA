package kr.co.talk.domain.chatroom.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import kr.co.talk.domain.chatroomusers.entity.ChatroomUsers;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
public class Chatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chatroomId;

    private String name;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createTime;

    @UpdateTimestamp
    private Timestamp lastUpdateTime;

    private long timeout; // 기본 30분

    private String teamCode;

    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ChatroomUsers> chatroomUsers = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        // timeout default value 30분
        this.timeout = this.timeout == 0 ? 1800000 : this.timeout;
    }
}
