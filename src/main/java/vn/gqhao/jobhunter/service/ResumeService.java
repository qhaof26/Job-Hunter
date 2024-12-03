package vn.gqhao.jobhunter.service;

import com.turkraft.springfilter.boot.Filter;
import com.turkraft.springfilter.converter.FilterSpecification;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import com.turkraft.springfilter.parser.node.FilterNode;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.gqhao.jobhunter.domain.Job;
import vn.gqhao.jobhunter.domain.Resume;
import vn.gqhao.jobhunter.domain.User;
import vn.gqhao.jobhunter.dto.request.ResumeCreationRequest;
import vn.gqhao.jobhunter.dto.request.ResumeUpdateRequest;
import vn.gqhao.jobhunter.dto.response.ResultPaginationDTO;
import vn.gqhao.jobhunter.dto.response.ResumeCreationResponse;
import vn.gqhao.jobhunter.dto.response.ResumeResponse;
import vn.gqhao.jobhunter.dto.response.ResumeUpdateResponse;
import vn.gqhao.jobhunter.exception.AppException;
import vn.gqhao.jobhunter.exception.ErrorCode;
import vn.gqhao.jobhunter.repository.ResumeRepository;
import vn.gqhao.jobhunter.util.SecurityUtil;
import vn.gqhao.jobhunter.util.mapper.ResumeMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResumeService {
    ResumeRepository resumeRepository;
    UserService userService;
    JobService jobService;
    ResumeMapper resumeMapper;

    @Autowired
    private FilterParser filterParser;

    @Autowired
    private FilterSpecificationConverter filterSpecificationConverter;

    public ResumeResponse handleFetchResumeById(long id){
        Resume resume = resumeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.RESUME_NOT_EXISTED));
        return resumeMapper.ResumeToResumeResponse(resume);
    }
//handleFetchResumesByUser
    public ResultPaginationDTO handleFetchResumesByUser(Pageable pageable){
        //query builder
        String email = SecurityUtil.getCurrentUserLogin().isPresent()
                ? SecurityUtil.getCurrentUserLogin().get() : "";
        FilterNode node = filterParser.parse("email='" + email + "'");
        FilterSpecification<Resume> spec = filterSpecificationConverter.convert(node);
        Page<Resume> pageResume = this.resumeRepository.findAll(spec, pageable);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());

        meta.setPages(pageResume.getTotalPages());
        meta.setTotal(pageResume.getTotalElements());

        rs.setMeta(meta);
        rs.setResult(pageResume);
        return rs;
    }


    public ResultPaginationDTO handleFetchAllResumes(Specification<Resume> spec, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Resume> pageResume = resumeRepository.findAll(spec, pageable);
        List<Resume> listResume = pageResume.getContent();
        List<ResumeResponse> resumeResponseList = new ArrayList<>();
        for(Resume resume : listResume){
            resumeResponseList.add(resumeMapper.ResumeToResumeResponse(resume));
        }

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pageResume.getTotalPages());
        meta.setTotal(pageResume.getTotalElements());

        rs.setMeta(meta);
        rs.setResult(resumeResponseList);
        return rs;
    }

    @Transactional
    public ResumeCreationResponse handleCreateResume(ResumeCreationRequest request){
        User userRequest = userService.fetchUserById(request.getUser().getId());
        Job jobRequest = jobService.handleFetchJobById(request.getJob().getId());
        Resume resume = Resume.builder()
                .email(request.getEmail())
                .url(request.getUrl())
                .status(request.getStatus())
                .user(userRequest)
                .job(jobRequest)
                .build();
        resumeRepository.save(resume);
        return resumeMapper.ResumeToResumeCreationResponse(resume);
    }

    @Transactional
    public ResumeUpdateResponse handleUpdateResume(ResumeUpdateRequest request){
        Resume resume = resumeRepository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.RESUME_NOT_EXISTED));
        resume.setStatus(request.getStatus());
        resumeRepository.save(resume);
        return resumeMapper.ResumeToResumeUpdateResponse(resume);
    }

    @Transactional
    public void handleDeleteResume(long id){
        Resume resume = resumeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.RESUME_NOT_EXISTED));
        resumeRepository.delete(resume);
    }
}
