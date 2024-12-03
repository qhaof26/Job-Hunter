package vn.gqhao.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gqhao.jobhunter.domain.Resume;
import vn.gqhao.jobhunter.dto.request.ResumeCreationRequest;
import vn.gqhao.jobhunter.dto.request.ResumeUpdateRequest;
import vn.gqhao.jobhunter.dto.response.*;
import vn.gqhao.jobhunter.service.ResumeService;
import vn.gqhao.jobhunter.util.annotation.ApiMessage;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1")
public class ResumeController {
    ResumeService resumeService;

    @GetMapping("/resumes/{id}")
    @ApiMessage("Fetch resume by id")
    public ResponseEntity<ResumeResponse> getResumeById(@PathVariable long id){
        ResumeResponse resumeResponse = resumeService.handleFetchResumeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(resumeResponse);
    }

    @GetMapping("/resumes")
    @ApiMessage("Fetch all resumes")
    public ResponseEntity<ResultPaginationDTO> getAllResumes(
            @Filter Specification<Resume> spec,
            @RequestParam(value = "page", required = false, defaultValue ="1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
            ){
        return ResponseEntity.status(HttpStatus.OK).body(resumeService.handleFetchAllResumes(spec, page, size));
    }

    @PostMapping("/resumes")
    @ApiMessage("Create resume")
    public ResponseEntity<ResumeCreationResponse> createNewResume(@Valid @RequestBody ResumeCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resumeService.handleCreateResume(request));
    }

    @PostMapping("/resumes/by-user")
    @ApiMessage("Get list resumes by user")
    public ResponseEntity<ResultPaginationDTO> getResumesByUser(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resumeService.handleFetchResumesByUser(pageable));
    }

    @PutMapping("/resumes/by-user")
    @ApiMessage("Update resume")
    public ResponseEntity<ResumeUpdateResponse> updateResume(@RequestBody ResumeUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resumeService.handleUpdateResume(request));
    }

    @DeleteMapping("/resumes/{id}")
    @ApiMessage("Delete resume")
    public ResponseEntity<String> removeResume(@PathVariable long id){
        resumeService.handleDeleteResume(id);
        return ResponseEntity.status(HttpStatus.OK).body("Xóa thành công !");
    }
}
