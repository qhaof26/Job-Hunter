package vn.gqhao.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.gqhao.jobhunter.entity.Permission;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    boolean existsByApiPathAndMethodAndModule(String apiPath, String method, String Module);
    List<Permission> findByIdIn(List<Long> id);
}
