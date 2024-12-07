package vn.gqhao.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gqhao.jobhunter.entity.Company;
import vn.gqhao.jobhunter.dto.response.ResultPaginationDTO;
import vn.gqhao.jobhunter.service.CompanyService;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("${api.prefix}/companies")
public class CompanyController {

    CompanyService companyService;

    @PostMapping()
    public ResponseEntity<?> createCompany(@Valid @RequestBody Company reqCompany){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.handleCreateCompany(reqCompany));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable long id){
        Company company = companyService.handleFetchCompanyById(id);
        return ResponseEntity.status(HttpStatus.OK).body(company);
    }

    @GetMapping()
    public ResponseEntity<ResultPaginationDTO> getAllCompany(
            @Filter Specification<Company> spec,
            @RequestParam(value = "page", required = false, defaultValue ="1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
            ){
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.handleFetchAllCompany(spec, pageable));
    }

    @PutMapping()
    public ResponseEntity<?> updateCompany(@Valid @RequestBody Company reqCompany){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.companyService.handleUpdateCompany(reqCompany));
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cập nhật không thành công");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@Min(value = 0) @PathVariable long id){
        this.companyService.handleDeleteCompany(id);
        return ResponseEntity.status(HttpStatus.OK).body("Xóa thành công");
    }
}
