package vn.gqhao.jobhunter.util.mapper;

import org.springframework.stereotype.Component;
import vn.gqhao.jobhunter.domain.Permission;
import vn.gqhao.jobhunter.dto.response.PermissionResponse;

@Component
public class PermissionMapper {
    public PermissionResponse PermissionToPermissionResponse(Permission permission){
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .apiPath(permission.getApiPath())
                .method(permission.getMethod())
                .module(permission.getModule())
                .createdAt(permission.getCreatedAt())
                .createdBy(permission.getCreatedBy())
                .updatedAt(permission.getUpdatedAt())
                .updatedBy(permission.getUpdatedBy())
                .build();
    }
}
