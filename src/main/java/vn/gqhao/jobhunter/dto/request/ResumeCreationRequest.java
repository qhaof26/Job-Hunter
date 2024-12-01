package vn.gqhao.jobhunter.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.gqhao.jobhunter.domain.Job;
import vn.gqhao.jobhunter.domain.User;
import vn.gqhao.jobhunter.util.constant.ResumeStateEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResumeCreationRequest {
    @NotBlank(message = "Email must be not null")
    String email;
    @NotBlank(message = "URL must be not null")
    String url;
    @Enumerated(EnumType.STRING)
    ResumeStateEnum status;
    User user;
    Job job;
}
