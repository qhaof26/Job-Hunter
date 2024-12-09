package vn.gqhao.jobhunter.dto.response;

import lombok.*;
import vn.gqhao.jobhunter.entity.Skill;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriberResponse {
    private Long id;
    private String email;
    private String name;
    private List<Skill> skills;
}
