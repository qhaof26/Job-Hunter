package vn.gqhao.jobhunter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "permissions")
public class Permission extends AbstractEntity<Long>{
    String name;
    String apiPath;
    String method;
    String module;

    public Permission(String name, String apiPath, String method, String module){
        this.name = name;
        this.apiPath = apiPath;
        this.method = method;
        this.module = module;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
    @JsonIgnore
    private List<Role> roles;

}
