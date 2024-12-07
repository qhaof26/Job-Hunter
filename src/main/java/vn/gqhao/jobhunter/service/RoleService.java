package vn.gqhao.jobhunter.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.gqhao.jobhunter.entity.Permission;
import vn.gqhao.jobhunter.entity.Role;
import vn.gqhao.jobhunter.dto.request.RoleCreationRequest;
import vn.gqhao.jobhunter.dto.request.RoleUpdateRequest;
import vn.gqhao.jobhunter.dto.response.ResultPaginationDTO;
import vn.gqhao.jobhunter.dto.response.RoleResponse;
import vn.gqhao.jobhunter.exception.AppException;
import vn.gqhao.jobhunter.exception.ErrorCode;
import vn.gqhao.jobhunter.repository.PermissionRepository;
import vn.gqhao.jobhunter.repository.RoleRepository;
import vn.gqhao.jobhunter.util.mapper.RoleMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public Role handleFetchRoleById(long id){
        return roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
    }

    public ResultPaginationDTO handleFetchAllRoles(Specification<Role> spec, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Role> pageRole = roleRepository.findAll(spec, pageable);
        List<Role> listRole = pageRole.getContent();
        List<RoleResponse> roleResponseList = new ArrayList<>();
        for(Role role : listRole){
            roleResponseList.add(roleMapper.RoleToRoleResponse(role));
        }

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pageRole.getTotalPages());
        meta.setTotal(pageRole.getTotalElements());

        rs.setMeta(meta);
        rs.setResult(roleResponseList);
        return rs;
    }

    @Transactional
    public RoleResponse handleCreateRole(RoleCreationRequest request){
        if(roleRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        List<Long> permissionsId = request.getPermissions()
                .stream().map(Permission::getId)
                .collect(Collectors.toList());
        List<Permission> permissions = permissionRepository.findByIdIn(permissionsId);
        if(permissions.isEmpty()){
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
        }

        Role role = roleMapper.RoleCreationRequestToRole(request);
        role.setPermissions(permissions);
        roleRepository.save(role);
        return roleMapper.RoleToRoleResponse(role);
    }

    @Transactional
    public RoleResponse handleUpdateRole(RoleUpdateRequest request){
//        if(roleRepository.existsByName(request.getName())){
//            throw new AppException(ErrorCode.ROLE_EXISTED);
//        }
        Role role = roleRepository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        List<Long> permissionsId = request.getPermissions()
                .stream().map(Permission::getId)
                .collect(Collectors.toList());
        List<Permission> permissions = permissionRepository.findByIdIn(permissionsId);
        if(permissions.isEmpty()){
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
        }
        role.setPermissions(permissions);
        roleMapper.updateRole(role, request);
        roleRepository.save(role);
        return roleMapper.RoleToRoleResponse(role);
    }

    @Transactional
    public void handleDeleteRole(long id){
        Role role = handleFetchRoleById(id);
        roleRepository.delete(role);
    }
}
