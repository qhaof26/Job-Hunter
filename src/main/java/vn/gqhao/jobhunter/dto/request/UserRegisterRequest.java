package vn.gqhao.jobhunter.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import vn.gqhao.jobhunter.util.constant.GenderEnum;

@Getter
@Setter
public class UserRegisterRequest {
    @NotBlank(message = "username không được để trống")
    private String email;

    @NotBlank(message = "password không được để trống")
    private String password;
    private String name;
    private int age;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;
}
