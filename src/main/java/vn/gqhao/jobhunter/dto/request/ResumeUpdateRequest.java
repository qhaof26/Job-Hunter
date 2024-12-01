package vn.gqhao.jobhunter.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.gqhao.jobhunter.util.constant.ResumeStateEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResumeUpdateRequest {
    @NotBlank(message = "Status must be not null")
    long id;
    @NotBlank(message = "Status must be not null")
    @Enumerated(EnumType.STRING)
    ResumeStateEnum status;
}
