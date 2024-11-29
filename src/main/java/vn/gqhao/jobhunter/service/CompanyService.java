package vn.gqhao.jobhunter.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.gqhao.jobhunter.domain.Company;
import vn.gqhao.jobhunter.domain.User;
import vn.gqhao.jobhunter.dto.response.ResultPaginationDTO;
import vn.gqhao.jobhunter.repository.CompanyRepository;
import vn.gqhao.jobhunter.repository.UserRepository;
import vn.gqhao.jobhunter.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

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

    public Company handleGetCompany(long id){
        return this.companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company not found !"));
    }

    @Transactional
    public Company handleUpdateCompany(Company companyDTO){
        Company company = handleGetCompany(companyDTO.getId());
        company.setName(company.getName());
        company.setDescription(companyDTO.getDescription());
        company.setAddress(companyDTO.getAddress());
        company.setLogo(companyDTO.getLogo());
        return company;
    }

    @Transactional
    public void handleDeleteCompany(long id){
        Company company = handleGetCompany(id);
        List<User> users = company.getUsers();
        for(User user : users){
            user.setCompany(null);
            this.userRepository.save(user);
        }
        this.companyRepository.delete(company);
    }
}
