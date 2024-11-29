package vn.gqhao.jobhunter.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.gqhao.jobhunter.domain.Company;
import vn.gqhao.jobhunter.domain.User;
import vn.gqhao.jobhunter.dto.request.UserCreationRequest;
import vn.gqhao.jobhunter.dto.request.UserUpdateRequest;
import vn.gqhao.jobhunter.dto.response.ResultPaginationDTO;
import vn.gqhao.jobhunter.dto.response.UserCreationResponse;
import vn.gqhao.jobhunter.dto.response.UserResponse;
import vn.gqhao.jobhunter.dto.response.UserUpdateResponse;
import vn.gqhao.jobhunter.repository.CompanyRepository;
import vn.gqhao.jobhunter.repository.UserRepository;
import vn.gqhao.jobhunter.util.error.ResourceNotFoundException;
import vn.gqhao.jobhunter.util.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserCreationResponse handleCreateUser(UserCreationRequest reqUser) {
        if(this.userRepository.existsUserByEmail(reqUser.getEmail())){
            throw new RuntimeException("Add fail !");
        }
        Company company = this.companyRepository.findById(reqUser.getCompanyId()).isPresent()
                ? companyRepository.getCompanyById(reqUser.getCompanyId()) : null;

        User user = User.builder()
                .name(reqUser.getName())
                .email(reqUser.getEmail())
                .password(reqUser.getPassword())
                .age(reqUser.getAge())
                .gender(reqUser.getGender())
                .address(reqUser.getAddress())
                .company(company)
                .build();
        this.userRepository.save(user);
        return userMapper.UserToUserCreateResDTO(user);
    }

    public User fetchUserById(long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found !"));
    }

    public ResultPaginationDTO fetchAllUser(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(spec, pageable);

        // Map data from user to userResDTO
        List<User> listUser = pageUser.getContent();
        List<UserResponse> userResDTOList = new ArrayList<>();
        for(User user : listUser){
            userResDTOList.add(this.userMapper.UserToUserResDTO(user));
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
    public UserUpdateResponse handleUpdateUser(UserUpdateRequest reqUser) {
        User currentUser = this.fetchUserById(reqUser.getId());
        Company company = this.companyRepository.findById(reqUser.getCompanyId()).isPresent()
                ? companyRepository.getCompanyById(reqUser.getCompanyId()) : null;

        if (currentUser != null) {
            currentUser.setName(reqUser.getName());
            currentUser.setGender(reqUser.getGender());
            currentUser.setAge(reqUser.getAge());
            currentUser.setAddress(reqUser.getAddress());
            currentUser.setCompany(company);
            // update
            currentUser = this.userRepository.save(currentUser);
        }
        return userMapper.UserToUserUpdateResDTO(currentUser);
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
