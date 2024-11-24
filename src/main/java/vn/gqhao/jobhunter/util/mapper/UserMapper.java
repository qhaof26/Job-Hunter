package vn.gqhao.jobhunter.util.mapper;

import org.springframework.stereotype.Component;
import vn.gqhao.jobhunter.domain.User;
import vn.gqhao.jobhunter.domain.response.user.UserCreateResDTO;
import vn.gqhao.jobhunter.domain.response.user.UserResDTO;
import vn.gqhao.jobhunter.domain.response.user.UserUpdateResDTO;

@Component
public class UserMapper {
    public UserCreateResDTO UserToUserCreateResDTO(User user){
        return new UserCreateResDTO(
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
    public UserUpdateResDTO UserToUserUpdateResDTO(User user){
        return new UserUpdateResDTO(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getGender(),
                user.getAddress(),
                user.getUpdatedAt(),
                user.getUpdatedBy()
        );
    }
    public UserResDTO UserToUserResDTO(User user){
        return new UserResDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getGender(),
                user.getAddress(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
