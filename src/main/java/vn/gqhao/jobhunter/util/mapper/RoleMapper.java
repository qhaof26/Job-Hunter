package vn.gqhao.jobhunter.util.mapper;

import org.springframework.stereotype.Component;
import vn.gqhao.jobhunter.domain.Permission;
import vn.gqhao.jobhunter.domain.Role;
import vn.gqhao.jobhunter.dto.request.RoleCreationRequest;
import vn.gqhao.jobhunter.dto.request.RoleUpdateRequest;
import vn.gqhao.jobhunter.dto.response.RoleResponse;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleMapper {
    public RoleResponse RoleToRoleResponse(Role role){
        List<RoleResponse.Permissions> permissions = new ArrayList<>();
        List<Permission> permissionList = role.getPermissions();
        for(Permission p : permissionList){
            permissions.add(new RoleResponse.Permissions(p.getId(), p.getName(), p.getApiPath(), p.getMethod()));
        }
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .active(role.isActive())
                .permissions(permissions)
                .build();
    }
    public Role RoleCreationRequestToRole(RoleCreationRequest request){
        return Role.builder()
                .name(request.getName())
                .active(request.isActive())
                .description(request.getDescription())
                .build();

    }
    public void updateRole(Role role, RoleUpdateRequest request){
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        role.setActive(request.isActive());
        role.setPermissions(request.getPermissions());
    }
}
