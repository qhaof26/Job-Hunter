package vn.gqhao.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @PostMapping("/users")
    @ApiMessage("Create user")
    public ResponseEntity<UserCreationResponse> createNewUser(@RequestBody UserCreationRequest postManUser) {
        String hashPassword = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
        UserCreationResponse user = this.userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/users/{id}")
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

    @GetMapping("/users/{id}")
    @ApiMessage("Get user by id")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userMapper.UserToUserResDTO(this.userService.fetchUserById(id)));
    }

    @GetMapping("/users")
    @ApiMessage("Fetch all users")
    public ResponseEntity<ResultPaginationDTO> getAllUser(
            @Filter Specification<User> spec,
            Pageable pageable
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUser(spec, pageable));
    }

    @PutMapping("/users")
    @ApiMessage("Update user")
    public ResponseEntity<UserUpdateResponse> updateUser(@RequestBody UserUpdateRequest user) {
        UserUpdateResponse userUpdate = this.userService.handleUpdateUser(user);
        return ResponseEntity.ok(userUpdate);
    }

}
