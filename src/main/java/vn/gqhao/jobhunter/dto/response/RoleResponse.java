package vn.gqhao.jobhunter.dto.response;

import lombok.*;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {
    private long id;
    private String name;
    private String description;
    private boolean active;
    private List<Permissions> permissions;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Permissions{
        private long id;
        private String name;
        private String apiPath;
        private String method;
    }
}
