package vn.gqhao.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gqhao.jobhunter.domain.Skill;
import vn.gqhao.jobhunter.dto.response.ResultPaginationDTO;
import vn.gqhao.jobhunter.service.SkillService;
import vn.gqhao.jobhunter.util.annotation.ApiMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SkillController {
    private final SkillService skillService;

    @GetMapping("/skills")
    @ApiMessage("Fetch all skills")
    public ResponseEntity<ResultPaginationDTO> getAllSkill(
            @Filter Specification<Skill> spec,
            Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.skillService.handleFetchAllSkill(spec, pageable));
    }

    @GetMapping("/skills/{id}")
    @ApiMessage("Fetch skill by id")
    public ResponseEntity<Skill> getSkillById(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.skillService.handleFetchSkillById(id));
    }

    @PostMapping("/skills")
    @ApiMessage("Create skill")
    public ResponseEntity<Skill> createNewSkill(@RequestBody Skill skill) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.handleCreateSkill(skill));
    }

    @PutMapping("/skills")
    @ApiMessage("Update skill")
    public ResponseEntity<?> updateSkill(@Valid @RequestBody Skill skill){
        return ResponseEntity.status(HttpStatus.OK).body(this.skillService.handleUpdateSkill(skill));
    }

    @DeleteMapping("/skills/{id}")
    @ApiMessage("Remove skill")
    public ResponseEntity<String> removeSkill(@PathVariable long id){
        skillService.handleRemoveSkill(id);
        return ResponseEntity.status(HttpStatus.OK).body("Xóa thành công");
    }
}
