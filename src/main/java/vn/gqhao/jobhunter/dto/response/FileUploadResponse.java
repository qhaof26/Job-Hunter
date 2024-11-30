package vn.gqhao.jobhunter.dto.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileUploadResponse {
    private String fileName;
    private Instant uploadedAt;
}
