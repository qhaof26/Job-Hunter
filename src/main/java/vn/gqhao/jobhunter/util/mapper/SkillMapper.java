package vn.gqhao.jobhunter.util.mapper;

import org.springframework.stereotype.Component;
import vn.gqhao.jobhunter.entity.Skill;
import vn.gqhao.jobhunter.dto.response.SkillResponse;

@Component
public class SkillMapper {
    public SkillResponse SKillToSkillResponse(Skill skill){
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .createdAt(skill.getCreatedAt())
                .createdBy(skill.getCreatedBy())
                .updatedAt(skill.getUpdatedAt())
                .updatedBy(skill.getUpdatedBy())
                .build();
    }
}
