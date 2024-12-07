package vn.gqhao.jobhunter.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.gqhao.jobhunter.entity.Company;
import vn.gqhao.jobhunter.entity.Role;
import vn.gqhao.jobhunter.entity.User;
import vn.gqhao.jobhunter.dto.request.UserCreationRequest;
import vn.gqhao.jobhunter.dto.request.UserRegisterRequest;
import vn.gqhao.jobhunter.dto.request.UserUpdateRequest;
import vn.gqhao.jobhunter.dto.response.ResultPaginationDTO;
import vn.gqhao.jobhunter.dto.response.UserCreationResponse;
import vn.gqhao.jobhunter.dto.response.UserResponse;
import vn.gqhao.jobhunter.dto.response.UserUpdateResponse;
import vn.gqhao.jobhunter.exception.AppException;
import vn.gqhao.jobhunter.exception.ErrorCode;
import vn.gqhao.jobhunter.repository.UserRepository;
import vn.gqhao.jobhunter.exception.ResourceNotFoundException;
import vn.gqhao.jobhunter.util.mapper.UserMapper;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleService roleService;
    CompanyService companyService;
    PasswordEncoder passwordEncoder;

    @Transactional
    public UserCreationResponse handleCreateUser(UserCreationRequest request) {
        if(this.userRepository.existsUserByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        Company company = companyService.handleFetchCompanyById(request.getCompany().getId());
        Role role = roleService.handleFetchRoleById(request.getRole().getId());

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .age(request.getAge())
                .gender(request.getGender())
                .address(request.getAddress())
                .company(company)
                .role(role)
                .build();
        this.userRepository.save(user);
        return userMapper.UserToUserCreationResponse(user);
    }

    @Transactional
    public UserCreationResponse handleRegisterUser(UserRegisterRequest request) {
        if(this.userRepository.existsUserByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        String hashPassword = this.passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(hashPassword)
                .age(request.getAge())
                .gender(request.getGender())
                .address(request.getAddress())
                .build();
        this.userRepository.save(user);
        return userMapper.UserToUserCreationResponse(user);
    }
    
    public User fetchUserById(long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_EXISTED.getMessage()));
    }

    public ResultPaginationDTO fetchAllUser(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(spec, pageable);

        // Map data from user to userResDTO
        List<User> listUser = pageUser.getContent();
        List<UserResponse> userResDTOList = new ArrayList<>();
        for(User user : listUser){
            userResDTOList.add(this.userMapper.UserToUserResponse(user));
        }

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1); //Đang ở trang nào ?
        meta.setPageSize(pageable.getPageSize());   //Kích thước / 1 trang

        meta.setPages(pageUser.getTotalPages());    //Tổng số trang
        meta.setTotal(pageUser.getTotalElements()); //Tổng số bản ghi

        rs.setMeta(meta);
        rs.setResult(userResDTOList);
        return rs;
    }

    @Transactional
    public UserUpdateResponse handleUpdateUser(UserUpdateRequest request) {
        User currentUser = this.fetchUserById(request.getId());
        Company company = companyService.handleFetchCompanyById(request.getCompany().getId());
        Role role = roleService.handleFetchRoleById(request.getRole().getId());
        if (currentUser != null) {
            currentUser.setName(request.getName());
            currentUser.setGender(request.getGender());
            currentUser.setAge(request.getAge());
            currentUser.setAddress(request.getAddress());
            currentUser.setCompany(company);
            currentUser.setRole(role);
            // update
            currentUser = this.userRepository.save(currentUser);
        }
        return userMapper.UserToUserUpdateResponse(currentUser);
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }

    @Transactional
    public void handleDeleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    @Transactional
    public void updateUserToken(String token, String email){
        User currentUser = this.handleGetUserByUsername(email);
        if(currentUser != null){
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }

    public User getUserByRefreshTokenAndEmail(String token, String email) {
        return this.userRepository.findByRefreshTokenAndEmail(token, email);
    }
}
