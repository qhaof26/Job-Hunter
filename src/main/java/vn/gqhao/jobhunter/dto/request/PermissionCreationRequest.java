package vn.gqhao.jobhunter.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionCreationRequest {
    private String name;
    private String apiPath;
    private String method;
    private String module;
}
