package vn.gqhao.jobhunter.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.gqhao.jobhunter.util.constant.ResumeStateEnum;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResumeResponse {
    long id;
    String email;
    String url;
    @Enumerated(EnumType.STRING)
    ResumeStateEnum status;
    String companyName;
    Instant createdAt;
    Instant updatedAt;
    String createdBy;
    String updatedBy;
    User user;
    Job job;

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class User{
        long id;
        String name;
    }

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Job{
        long id;
        String name;
    }
}
