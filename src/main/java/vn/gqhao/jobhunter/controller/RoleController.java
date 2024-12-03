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
import vn.gqhao.jobhunter.domain.Role;
import vn.gqhao.jobhunter.dto.request.RoleCreationRequest;
import vn.gqhao.jobhunter.dto.request.RoleUpdateRequest;
import vn.gqhao.jobhunter.dto.response.ResultPaginationDTO;
import vn.gqhao.jobhunter.dto.response.RoleResponse;
import vn.gqhao.jobhunter.service.RoleService;
import vn.gqhao.jobhunter.util.annotation.ApiMessage;
import vn.gqhao.jobhunter.util.mapper.RoleMapper;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;
    RoleMapper roleMapper;

    @GetMapping("/roles/{id}")
    @ApiMessage("Fetch a role")
    public ResponseEntity<RoleResponse> fetchRoleById(@PathVariable long id){
        Role role = roleService.handleFetchRoleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(roleMapper.RoleToRoleResponse(role));
    }

    @GetMapping("/roles")
    @ApiMessage("Fetch all roles")
    public ResponseEntity<ResultPaginationDTO> fetchAllRoles(
            @Filter Specification<Role> spec,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
            ){
        return ResponseEntity.status(HttpStatus.OK).body(roleService.handleFetchAllRoles(spec, page, size));
    }

    @PostMapping("/roles")
    @ApiMessage("Create role")
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleCreationRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(roleService.handleCreateRole(request));
    }

    @PutMapping("/roles")
    @ApiMessage("Update role")
    public ResponseEntity<RoleResponse> updateRole(@Valid @RequestBody RoleUpdateRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(roleService.handleUpdateRole(request));
    }

    @DeleteMapping("/roles/{id}")
    @ApiMessage("Delete role")
    public ResponseEntity<String> deleteRole(@PathVariable long id){
        roleService.handleDeleteRole(id);
        return ResponseEntity.status(HttpStatus.OK).body("Xóa thành công !");
    }
}
