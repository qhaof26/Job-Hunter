package vn.gqhao.jobhunter.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import vn.gqhao.jobhunter.domain.Company;
import vn.gqhao.jobhunter.domain.Role;
import vn.gqhao.jobhunter.util.constant.GenderEnum;

@Getter
@Setter
public class UserUpdateRequest {
    private long id;
    private String name;
    private int age;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;
    private Company company;
    private Role role;
}
