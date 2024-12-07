package vn.gqhao.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.gqhao.jobhunter.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByEmail(String email);
    boolean existsUserByEmail(String email);
    boolean existsUserById(long id);
    User findByRefreshTokenAndEmail(String token, String email);
}
