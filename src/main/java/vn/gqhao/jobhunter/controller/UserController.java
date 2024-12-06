package vn.gqhao.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import vn.gqhao.jobhunter.domain.User;
import vn.gqhao.jobhunter.dto.request.UserCreationRequest;
import vn.gqhao.jobhunter.dto.request.UserUpdateRequest;
import vn.gqhao.jobhunter.dto.response.ResultPaginationDTO;
import vn.gqhao.jobhunter.dto.response.UserCreationResponse;
import vn.gqhao.jobhunter.dto.response.UserResponse;
import vn.gqhao.jobhunter.dto.response.UserUpdateResponse;
import vn.gqhao.jobhunter.service.UserService;
import vn.gqhao.jobhunter.util.annotation.ApiMessage;
import vn.gqhao.jobhunter.exception.IdInvalidException;
import vn.gqhao.jobhunter.util.mapper.UserMapper;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @PostMapping()
    @ApiMessage("Create user")
    public ResponseEntity<UserCreationResponse> createNewUser(@RequestBody UserCreationRequest postManUser) {
        String hashPassword = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
        UserCreationResponse user = this.userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete user")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id)
            throws IdInvalidException {
        User currentUser = this.userService.fetchUserById(id);
        if(currentUser == null){
            throw new IdInvalidException("User với id = " + id + " không tồn tại !");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}")
    @ApiMessage("Get user by id")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userMapper.UserToUserResponse(this.userService.fetchUserById(id)));
    }

    @GetMapping()
    @ApiMessage("Fetch all users")
    public ResponseEntity<ResultPaginationDTO> getAllUser(
            @Filter Specification<User> spec,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
            ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUser(spec, pageable));
    }

    @PutMapping()
    @ApiMessage("Update user")
    public ResponseEntity<UserUpdateResponse> updateUser(@RequestBody UserUpdateRequest user) {
        UserUpdateResponse userUpdate = this.userService.handleUpdateUser(user);
        return ResponseEntity.ok(userUpdate);
    }

}
