package vn.gqhao.jobhunter.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.gqhao.jobhunter.util.constant.ResumeStateEnum;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "resumes")
public class Resume extends AbstractEntity<Long>{
    String email;
    String url;
    @Enumerated(EnumType.STRING)
    ResumeStateEnum status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "job_id")
    Job job;
}
