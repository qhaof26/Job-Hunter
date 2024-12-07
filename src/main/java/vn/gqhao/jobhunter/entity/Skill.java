package vn.gqhao.jobhunter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.gqhao.jobhunter.util.SecurityUtil;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "skills")
    @JsonIgnore
    private List<Job> jobs;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "skills")
    @JsonIgnore
    private List<Subscriber> subscribers;

    @PrePersist
    public void handleBeforeCreate(){
        this.createdAt = Instant.now();
        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
    }

    @PreUpdate
    public void handleBeforeUpdate(){
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
    }

}
