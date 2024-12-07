package vn.gqhao.jobhunter.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.gqhao.jobhunter.entity.Skill;
import vn.gqhao.jobhunter.dto.response.ResultPaginationDTO;
import vn.gqhao.jobhunter.dto.response.SkillResponse;
import vn.gqhao.jobhunter.repository.SkillRepository;
import vn.gqhao.jobhunter.exception.AppException;
import vn.gqhao.jobhunter.exception.ErrorCode;
import vn.gqhao.jobhunter.util.mapper.SkillMapper;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SkillService {
    SkillRepository skillRepository;
    SkillMapper skillMapper;

    public ResultPaginationDTO handleFetchAllSkills(Specification<Skill> spec, Pageable pageable){
        Page<Skill> pageSkill = this.skillRepository.findAll(spec, pageable);
        List<Skill> skills = pageSkill.getContent();
        List<SkillResponse> skillResponseList = new ArrayList<>();
        for(Skill skill : skills){
            skillResponseList.add(skillMapper.SKillToSkillResponse(skill));
        }

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(pageSkill.getTotalPages());
        mt.setTotal(pageSkill.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(skillResponseList);

        return rs;
    }

    public Skill handleFetchSkillById(long id){
        return this.skillRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SKILL_NOT_EXISTED));
    }

    @Transactional
    public Skill handleCreateSkill(Skill skill){
        if(skillRepository.existsByName(skill.getName())){
            throw new AppException(ErrorCode.SKILL_EXISTED);
        }
        return this.skillRepository.save(skill);
    }

    @Transactional
    public Skill handleUpdateSkill(Skill skillDTO){
        Skill skill = handleFetchSkillById(skillDTO.getId());
        if(skillRepository.existsByName(skill.getName())){
            throw new AppException(ErrorCode.SKILL_EXISTED);
        }
        skill.setName(skillDTO.getName());
        return skillRepository.save(skill);
    }

    @Transactional
    public void handleRemoveSkill(long id){
        // delete job (inside job_skill table)
        Skill currentSkill = handleFetchSkillById(id);
        currentSkill.getJobs().forEach(job -> job.getSkills().remove(currentSkill));
        skillRepository.delete(currentSkill);
    }
}
