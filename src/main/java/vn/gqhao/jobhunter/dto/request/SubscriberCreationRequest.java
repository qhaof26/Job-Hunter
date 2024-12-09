package vn.gqhao.jobhunter.dto.request;

import lombok.*;
import vn.gqhao.jobhunter.entity.Skill;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriberCreationRequest {
    private String email;
    private String name;
    private List<Skill> skills;
}
