package vn.gqhao.jobhunter.util.mapper;

import org.springframework.stereotype.Component;
import vn.gqhao.jobhunter.domain.Job;
import vn.gqhao.jobhunter.domain.Skill;
import vn.gqhao.jobhunter.dto.request.JobCreationRequest;
import vn.gqhao.jobhunter.dto.request.JobUpdateRequest;
import vn.gqhao.jobhunter.dto.response.JobCreationResponse;
import vn.gqhao.jobhunter.dto.response.JobResponse;
import vn.gqhao.jobhunter.dto.response.JobUpdateResponse;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobMapper {
    //JobCreationRequest -> Job
    public Job JobCreationRequestToJob(JobCreationRequest request){
        return Job.builder()
                .name(request.getName())
                .location(request.getLocation())
                .salary(request.getSalary())
                .quantity(request.getQuantity())
                .levelEnum(request.getLevelEnum())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .active(request.isActive())
                .build();
    }
    //JobUpdateRequest -> Job
    public void JobUpdateRequestToJob(JobUpdateRequest request, Job job){
        job.setName(request.getName());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setQuantity(request.getQuantity());
        job.setLevelEnum(request.getLevelEnum());
        job.setDescription(request.getDescription());
        job.setStartDate(request.getStartDate());
        job.setEndDate(request.getEndDate());
        job.setActive(request.isActive());
    }
    //Job -> JobCreationResponse
    public JobCreationResponse JobToJobCreationResponse(Job job){
        JobCreationResponse jobCreationResponse = JobCreationResponse.builder()
                .id(job.getId())
                .name(job.getName())
                .location(job.getLocation())
                .salary(job.getSalary())
                .quantity(job.getQuantity())
                .levelEnum(job.getLevelEnum())
                .description(job.getDescription())
                .startDate(job.getStartDate())
                .endDate(job.getEndDate())
                .createdAt(job.getCreatedAt())
                .createdBy(job.getCreatedBy())
                .active(job.isActive())
                .build();

        List<String> nameSkills = job.getSkills()
                .stream().map(Skill::getName)
                .collect(Collectors.toList());
        jobCreationResponse.setSkills(nameSkills);
        return jobCreationResponse;
    }
    //Job -> JobUpdateResponse
    public JobUpdateResponse JobToJobUpdateResponse(Job job){
        JobUpdateResponse jobUpdateResponse = JobUpdateResponse.builder()
                .id(job.getId())
                .name(job.getName())
                .location(job.getLocation())
                .salary(job.getSalary())
                .quantity(job.getQuantity())
                .levelEnum(job.getLevelEnum())
                .description(job.getDescription())
                .startDate(job.getStartDate())
                .endDate(job.getEndDate())
                .updatedAt(job.getUpdatedAt())
                .updatedBy(job.getUpdatedBy())
                .active(job.isActive())
                .build();

        List<String> nameSkills = job.getSkills()
                .stream().map(Skill::getName)
                .collect(Collectors.toList());
        jobUpdateResponse.setSkills(nameSkills);
        return jobUpdateResponse;
    }
    //Job -> JobResponse
    public JobResponse JobToJobResponse(Job job){
        JobResponse jobResponse = JobResponse.builder()
                .id(job.getId())
                .name(job.getName())
                .location(job.getLocation())
                .salary(job.getSalary())
                .quantity(job.getQuantity())
                .levelEnum(job.getLevelEnum())
                .description(job.getDescription())
                .startDate(job.getStartDate())
                .endDate(job.getEndDate())
                .createdAt(job.getCreatedAt())
                .createdBy(job.getCreatedBy())
                .active(job.isActive())
                .build();

        List<String> nameSkills = job.getSkills()
                .stream().map(Skill::getName)
                .collect(Collectors.toList());
        jobResponse.setSkills(nameSkills);
        return jobResponse;
    }
}
