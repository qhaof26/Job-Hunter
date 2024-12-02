package vn.gqhao.jobhunter.dto.request;

import lombok.*;
import vn.gqhao.jobhunter.domain.Permission;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleCreationRequest {
    private String name;
    private String description;
    private boolean active;
    private List<Permission> permissions;
}
