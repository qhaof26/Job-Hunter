package vn.gqhao.jobhunter.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionUpdateRequest {
    private long id;
    private String name;
    private String apiPath;
    private String method;
    private String module;
}
