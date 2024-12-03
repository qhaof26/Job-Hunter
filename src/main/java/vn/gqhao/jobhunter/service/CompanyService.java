package vn.gqhao.jobhunter.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.gqhao.jobhunter.domain.Company;
import vn.gqhao.jobhunter.domain.Job;
import vn.gqhao.jobhunter.domain.User;
import vn.gqhao.jobhunter.dto.response.ResultPaginationDTO;
import vn.gqhao.jobhunter.exception.ErrorCode;
import vn.gqhao.jobhunter.repository.CompanyRepository;
import vn.gqhao.jobhunter.repository.JobRepository;
import vn.gqhao.jobhunter.repository.UserRepository;
import vn.gqhao.jobhunter.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyService {
    CompanyRepository companyRepository;
    UserRepository userRepository;
    JobRepository jobRepository;

    @Transactional
    public Company handleCreateCompany(Company company){
        return this.companyRepository.save(company);
    }

    public ResultPaginationDTO handleFetchAllCompany(Specification<Company> spec, Pageable pageable){
        Page<Company> pageCompany = this.companyRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();

        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageCompany.getTotalPages());
        mt.setTotal(pageCompany.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageCompany);

        return rs;
    }

    public Company handleFetchCompanyById(long id){
        return this.companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.COMPANY_NOT_EXISTED.getMessage()));
    }

    @Transactional
    public Company handleUpdateCompany(Company companyDTO){
        Company company = handleFetchCompanyById(companyDTO.getId());
        company.setName(company.getName());
        company.setDescription(companyDTO.getDescription());
        company.setAddress(companyDTO.getAddress());
        company.setLogo(companyDTO.getLogo());
        return company;
    }

    @Transactional
    public void handleDeleteCompany(long id){
        Company company = handleFetchCompanyById(id);
        List<User> users = company.getUsers();
        for(User user : users){
            user.setCompany(null);
            this.userRepository.save(user);
        }
//        List<Job> jobs = company.getJobs();
//        company.setJobs(null);
//        for(Job item : jobs) {
//            jobRepository.delete(item);
//        }

        this.companyRepository.delete(company);
    }
}
