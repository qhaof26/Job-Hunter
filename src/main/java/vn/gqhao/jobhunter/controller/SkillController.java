package vn.gqhao.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("${api.prefix}/skills")
public class SkillController {
    SkillService skillService;

    @GetMapping()
    @ApiMessage("Fetch all skills")
    public ResponseEntity<ResultPaginationDTO> getAllSkill(
            @Filter Specification<Skill> spec,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.status(HttpStatus.OK).body(this.skillService.handleFetchAllSkills(spec, pageable));
    }

    @GetMapping("/{id}")
    @ApiMessage("Fetch skill by id")
    public ResponseEntity<Skill> getSkillById(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.skillService.handleFetchSkillById(id));
    }

    @PostMapping()
    @ApiMessage("Create skill")
    public ResponseEntity<Skill> createNewSkill(@RequestBody Skill skill) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.handleCreateSkill(skill));
    }

    @PutMapping()
    @ApiMessage("Update skill")
    public ResponseEntity<?> updateSkill(@Valid @RequestBody Skill skill){
        return ResponseEntity.status(HttpStatus.OK).body(this.skillService.handleUpdateSkill(skill));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Remove skill")
    public ResponseEntity<String> removeSkill(@PathVariable long id){
        skillService.handleRemoveSkill(id);
        return ResponseEntity.status(HttpStatus.OK).body("Xóa thành công");
    }
}
