package vn.gqhao.jobhunter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.gqhao.jobhunter.entity.Role;

@Getter
@Setter
public class UserLoginResponse {
    @JsonProperty("access_token")
    private String accessToken;

    private UserLogin user;
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLogin{
        private long id;
        private String email;
        private String name;
        private Role role;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserGetAccount{
        private UserLogin user;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInsideToken{
        private long id;
        private String email;
        private String name;
    }
}
