package vn.gqhao.jobhunter.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResumeCreationResponse {
    long id;
    Instant createdAt;
    String createdBy;
}
