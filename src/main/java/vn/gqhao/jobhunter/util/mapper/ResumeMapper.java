package vn.gqhao.jobhunter.util.mapper;

import org.springframework.stereotype.Component;
import vn.gqhao.jobhunter.domain.Resume;
import vn.gqhao.jobhunter.dto.response.ResumeCreationResponse;
import vn.gqhao.jobhunter.dto.response.ResumeResponse;
import vn.gqhao.jobhunter.dto.response.ResumeUpdateResponse;

@Component
public class ResumeMapper {

    public ResumeCreationResponse ResumeToResumeCreationResponse(Resume resume){
        return ResumeCreationResponse.builder()
                .id(resume.getId())
                .createdAt(resume.getCreatedAt())
                .createdBy(resume.getCreatedBy())
                .build();
    }

    public ResumeUpdateResponse ResumeToResumeUpdateResponse(Resume resume){
        return ResumeUpdateResponse.builder()
                .id(resume.getId())
                .updatedAt(resume.getCreatedAt())
                .updatedBy(resume.getCreatedBy())
                .build();
    }

    public ResumeResponse ResumeToResumeResponse(Resume resume){
        ResumeResponse.User user = new ResumeResponse.User(resume.getUser().getId(), resume.getUser().getName());
        ResumeResponse.Job job = new ResumeResponse.Job(resume.getJob().getId(), resume.getJob().getName());
        String companyName = null;
        if(resume.getJob() != null){
            companyName = resume.getJob().getCompany().getName();
        }
        return ResumeResponse.builder()
                .id(resume.getId())
                .email(resume.getEmail())
                .url(resume.getUrl())
                .status(resume.getStatus())
                .companyName(companyName)
                .createdAt(resume.getCreatedAt())
                .createdBy(resume.getCreatedBy())
                .updatedAt(resume.getCreatedAt())
                .updatedBy(resume.getCreatedBy())
                .user(user)
                .job(job)
                .build();
    }
}
