package vn.gqhao.jobhunter.util.mapper;

import org.springframework.stereotype.Component;
import vn.gqhao.jobhunter.entity.Company;
import vn.gqhao.jobhunter.dto.response.CompanyResponse;

@Component
public class CompanyMapper {
    public CompanyResponse CompanyToCompanyResponse(Company company){
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .logo(company.getLogo())
                .description(company.getDescription())
                .address(company.getAddress())
                .createdAt(company.getCreatedAt())
                .createdBy(company.getCreatedBy())
                .updatedAt(company.getUpdatedAt())
                .updatedBy(company.getUpdatedBy())
                .build();
    }
}
