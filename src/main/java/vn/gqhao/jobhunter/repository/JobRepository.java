package vn.gqhao.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.gqhao.jobhunter.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
}
