package vn.gqhao.jobhunter.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.gqhao.jobhunter.domain.Job;
import vn.gqhao.jobhunter.domain.Skill;
import vn.gqhao.jobhunter.dto.request.JobCreationRequest;
import vn.gqhao.jobhunter.dto.request.JobUpdateRequest;
import vn.gqhao.jobhunter.dto.response.*;
import vn.gqhao.jobhunter.repository.CompanyRepository;
import vn.gqhao.jobhunter.repository.JobRepository;
import vn.gqhao.jobhunter.repository.SkillRepository;
import vn.gqhao.jobhunter.exception.AppException;
import vn.gqhao.jobhunter.exception.ErrorCode;
import vn.gqhao.jobhunter.util.mapper.JobMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobService {
    JobRepository jobRepository;
    SkillRepository skillRepository;
    JobMapper jobMapper;
    CompanyRepository companyRepository;

    public Job fetchJobById(long id){
        return jobRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_EXISTED));
    }

    public ResultPaginationDTO fetchAllJobs(Specification<Job> spec, Pageable pageable){
        Page<Job> pageJob = this.jobRepository.findAll(spec, pageable);

        // Map data from user to userResDTO
        List<Job> listJob = pageJob.getContent();
        List<JobResponse> jobListResponse = new ArrayList<>();
        for(Job job : listJob){
            jobListResponse.add(this.jobMapper.JobToJobResponse(job));
        }

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1); //Đang ở trang nào ?
        meta.setPageSize(pageable.getPageSize());   //Kích thước / 1 trang

        meta.setPages(pageJob.getTotalPages());    //Tổng số trang
        meta.setTotal(pageJob.getTotalElements()); //Tổng số bản ghi

        rs.setMeta(meta);
        rs.setResult(jobListResponse);
        return rs;
    }

    @Transactional
    public JobCreationResponse createJob(JobCreationRequest request){
        List<Long> listId = request.getSkills()
                .stream().map(Skill :: getId)
                .collect(Collectors.toList());
        List<Skill> skills = skillRepository.findByIdIn(listId);
        if(skills.isEmpty()){
            throw new AppException(ErrorCode.SKILL_NOT_EXISTED);
        }
        if(companyRepository.getCompanyById(request.getCompany().getId()) == null){
            throw new AppException(ErrorCode.COMPANY_NOT_EXISTED);
        }

        Job job = jobMapper.JobCreationRequestToJob(request);
        job.setSkills(skills);
        jobRepository.save(job);
        return jobMapper.JobToJobCreationResponse(job);
    }

    @Transactional
    public JobUpdateResponse updateJob(JobUpdateRequest request){
        Job job = fetchJobById(request.getId());
        List<Long> listId = request.getSkills()
                .stream().map(Skill :: getId)
                .collect(Collectors.toList());
        List<Skill> skills = skillRepository.findByIdIn(listId);
        if(skills.isEmpty()){
            throw new AppException(ErrorCode.SKILL_NOT_EXISTED);
        }
        if(companyRepository.getCompanyById(request.getCompany().getId()) == null){
            throw new AppException(ErrorCode.COMPANY_NOT_EXISTED);
        }

        jobMapper.JobUpdateRequestToJob(request, job);
        job.setSkills(skills);
        jobRepository.save(job);
        return jobMapper.JobToJobUpdateResponse(job);
    }

    @Transactional
    public void deleteJob(long id){
        Job job = fetchJobById(id);
        jobRepository.delete(job);
    }

    public JobResponse fetchJobResponse(Job job){
        return jobMapper.JobToJobResponse(job);
    }
}
