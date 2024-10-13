package vn.gqhao.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.gqhao.jobhunter.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
