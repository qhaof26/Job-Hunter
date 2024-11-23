package vn.gqhao.jobhunter.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.gqhao.jobhunter.util.SecurityUtil;
import vn.gqhao.jobhunter.util.constant.GenderEnum;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String password;
    private int age;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private String address;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshToken;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @PrePersist
    public void handleBeforeCreate(){
        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
                         ? SecurityUtil.getCurrentUserLogin().get() : " ";
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void handleBeforeUpdate(){
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
                         ? SecurityUtil.getCurrentUserLogin().get() : " ";
    }
}
