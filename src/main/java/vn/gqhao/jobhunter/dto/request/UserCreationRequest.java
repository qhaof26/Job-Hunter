package vn.gqhao.jobhunter.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import vn.gqhao.jobhunter.util.constant.GenderEnum;

@Getter
@Setter
public class UserCreationRequest {
    private String name;
    private String email;
    private String password;
    private int age;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;
    private long companyId;
}
