package vn.gqhao.jobhunter.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import vn.gqhao.jobhunter.util.constant.GenderEnum;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String name;
    private String email;
    private int age;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;
    private Instant createdAt;
    private Instant updatedAt;

    private Company company;
    @Getter
    @Setter
    public static class Company{
        private long idCompany;
        private String nameCompany;
    }
}
