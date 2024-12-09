package vn.gqhao.jobhunter.dto.request;

import lombok.*;
import vn.gqhao.jobhunter.entity.Skill;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriberUpdateRequest {
    private Long id;
    private List<Skill> skills;
}
