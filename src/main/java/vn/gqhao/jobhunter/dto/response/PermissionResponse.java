package vn.gqhao.jobhunter.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionResponse {
    private long id;
    private String name;
    private String apiPath;
    private String method;
    private String module;
    private Instant createdAt;
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant updatedAt;
    private String createdBy;
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private String updatedBy;
}
