package vn.gqhao.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gqhao.jobhunter.domain.Job;

import vn.gqhao.jobhunter.dto.request.JobCreationRequest;
import vn.gqhao.jobhunter.dto.request.JobUpdateRequest;
import vn.gqhao.jobhunter.dto.response.*;
import vn.gqhao.jobhunter.service.JobService;
import vn.gqhao.jobhunter.util.annotation.ApiMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class JobController {
    private final JobService jobService;

    @GetMapping("/jobs/{id}")
    @ApiMessage("Fetch job by id")
    public ResponseEntity<JobResponse> getJobById(@PathVariable long id){
        Job job = jobService.handleFetchJobById(id);
        return ResponseEntity.status(HttpStatus.OK).body(jobService.fetchJobResponse(job));
    }

    @GetMapping("/jobs")
    @ApiMessage("Fetch all jobs")
    public ResponseEntity<ResultPaginationDTO> getAllJobs(
            @Filter Specification<Job> spec,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
            ){
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.status(HttpStatus.OK).body(jobService.handleFetchAllJobs(spec, pageable));
    }

    @PostMapping("/jobs")
    @ApiMessage("Create job")
    public ResponseEntity<JobCreationResponse> createNewJob(@RequestBody JobCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.jobService.handleCreateJob(request));
    }

    @PutMapping("/jobs")
    @ApiMessage("Update job")
    public ResponseEntity<JobUpdateResponse> handleUpdateJob(@RequestBody JobUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.jobService.handleUpdateJob(request));
    }

    @DeleteMapping("/jobs/{id}")
    @ApiMessage("Delete job")
    public ResponseEntity<String> removeJob(@PathVariable long id){
        jobService.handleDeleteJob(id);
        return ResponseEntity.status(HttpStatus.OK).body("Xóa thành công !");
    }
}
