package vn.gqhao.jobhunter.util.mapper;

import org.springframework.stereotype.Component;
import vn.gqhao.jobhunter.entity.User;
import vn.gqhao.jobhunter.dto.response.UserCreationResponse;
import vn.gqhao.jobhunter.dto.response.UserResponse;
import vn.gqhao.jobhunter.dto.response.UserUpdateResponse;

@Component
public class UserMapper {
    // User -> ResCreateUserDTO: phản hồi khi tạo mới
    public UserCreationResponse UserToUserCreationResponse(User user){
        return new UserCreationResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getGender(),
                user.getAddress(),
                user.getCreatedAt(),
                user.getCreatedBy()
        );
    }

    // User -> UserUpdateResDTO: phản hồi khi update
    public UserUpdateResponse UserToUserUpdateResponse(User user){
        UserUpdateResponse.Company company = new UserUpdateResponse.Company();
        if(user.getCompany() != null){
            company.setIdCompany(user.getCompany().getId());
            company.setNameCompany(user.getCompany().getName());
        } else{
            company = null;
        }
        return UserUpdateResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .address(user.getAddress())
                .gender(user.getGender())
                .updatedBy(user.getUpdatedBy())
                .updatedAt(user.getUpdatedAt())
                .company(company)
                .build();
    }

    // User -> UserResDTO
    public UserResponse UserToUserResponse(User user){
        UserResponse.Company companyUser = new UserResponse.Company();
        if(user.getCompany() != null){
            companyUser.setIdCompany(user.getCompany().getId());
            companyUser.setNameCompany(user.getCompany().getName());
        } else{
            companyUser = null;
        }

        UserResponse.Role roleUser = new UserResponse.Role();
        if(user.getRole() != null){
            roleUser.setIdRole(user.getRole().getId());
            roleUser.setName(user.getRole().getName());
        } else{
            roleUser = null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .address(user.getAddress())
                .gender(user.getGender())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .company(companyUser)
                .role(roleUser)
                .build();
    }
}
