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
import vn.gqhao.jobhunter.domain.Permission;
import vn.gqhao.jobhunter.dto.request.PermissionCreationRequest;
import vn.gqhao.jobhunter.dto.request.PermissionUpdateRequest;
import vn.gqhao.jobhunter.dto.response.PermissionResponse;
import vn.gqhao.jobhunter.dto.response.ResultPaginationDTO;
import vn.gqhao.jobhunter.exception.AppException;
import vn.gqhao.jobhunter.exception.ErrorCode;
import vn.gqhao.jobhunter.repository.PermissionRepository;
import vn.gqhao.jobhunter.util.mapper.PermissionMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public Permission handleFetchPermissionById(long id){
        return permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
    }

    public ResultPaginationDTO handleFetchAllPermissions(Specification<Permission> spec, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Permission> pagePermission = permissionRepository.findAll(spec, pageable);
        List<Permission> listPermission = pagePermission.getContent();
        List<PermissionResponse> permissionResponseList = new ArrayList<>();
        for(Permission permission : listPermission){
            permissionResponseList.add(permissionMapper.PermissionToPermissionResponse(permission));
        }

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pagePermission.getTotalPages());
        meta.setTotal(pagePermission.getTotalElements());

        rs.setMeta(meta);
        rs.setResult(permissionResponseList);
        return rs;
    }

    @Transactional
    public PermissionResponse handleCreatePermission(PermissionCreationRequest request){
        if(permissionRepository.existsByApiPathAndMethodAndModule(request.getApiPath(), request.getMethod(), request.getModule())){
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        Permission permission = Permission.builder()
                .name(request.getName())
                .apiPath(request.getApiPath())
                .method(request.getMethod())
                .module(request.getModule())
                .build();
        permissionRepository.save(permission);
        return permissionMapper.PermissionToPermissionResponse(permission);
    }

    @Transactional
    public PermissionResponse handleUpdatePermission(PermissionUpdateRequest request){
        Permission permission = handleFetchPermissionById(request.getId());
        if(permissionRepository.existsByApiPathAndMethodAndModule(request.getApiPath(), request.getMethod(), request.getModule())){
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        permission.setName(request.getName());
        permission.setApiPath(request.getApiPath());
        permission.setMethod(request.getMethod());
        permission.setModule(request.getModule());
        permissionRepository.save(permission);
        return permissionMapper.PermissionToPermissionResponse(permission);
    }

    @Transactional
    public void handleDeletePermission(long id){
        Permission permission = handleFetchPermissionById(id);
        permission.getRoles().forEach(role -> role.getPermissions().remove(permission));
        permissionRepository.delete(permission);
    }
}
