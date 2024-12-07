package vn.gqhao.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gqhao.jobhunter.entity.Permission;
import vn.gqhao.jobhunter.dto.request.PermissionCreationRequest;
import vn.gqhao.jobhunter.dto.request.PermissionUpdateRequest;
import vn.gqhao.jobhunter.dto.response.PermissionResponse;
import vn.gqhao.jobhunter.dto.response.ResultPaginationDTO;
import vn.gqhao.jobhunter.service.PermissionService;
import vn.gqhao.jobhunter.util.annotation.ApiMessage;
import vn.gqhao.jobhunter.util.mapper.PermissionMapper;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;
    PermissionMapper permissionMapper;

    @GetMapping("/{id}")
    @ApiMessage("Fetch a permission")
    public ResponseEntity<PermissionResponse> fetchPermissionById(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(permissionMapper.PermissionToPermissionResponse(permissionService.handleFetchPermissionById(id)));
    }

    @GetMapping()
    @ApiMessage("Fetch all permissions")
    public ResponseEntity<ResultPaginationDTO> fetchAllPermissions(
            @Filter Specification<Permission> spec,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
            ){
        return ResponseEntity.status(HttpStatus.OK).body(permissionService.handleFetchAllPermissions(spec, page, size));
    }

    @PostMapping()
    @ApiMessage("Create permission")
    public ResponseEntity<PermissionResponse> createPermission(@Valid @RequestBody PermissionCreationRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(permissionService.handleCreatePermission(request));
    }

    @PutMapping()
    @ApiMessage("Update permission")
    public ResponseEntity<PermissionResponse> updatePermission(@Valid @RequestBody PermissionUpdateRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(permissionService.handleUpdatePermission(request));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete permission")
    public ResponseEntity<String> deletePermission(@PathVariable long id){
        permissionService.handleDeletePermission(id);
        return ResponseEntity.status(HttpStatus.OK).body("Xóa thành công !");
    }
}
