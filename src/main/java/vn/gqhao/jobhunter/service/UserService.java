package vn.gqhao.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.gqhao.jobhunter.domain.User;
import vn.gqhao.jobhunter.domain.dto.Meta;
import vn.gqhao.jobhunter.domain.dto.ResultPaginationDTO;
import vn.gqhao.jobhunter.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleDeleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public User fetchUserById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    public ResultPaginationDTO fetchAllUser(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta meta = new Meta();

        meta.setPage(pageable.getPageNumber() + 1); //Đang ở trang nào ?
        meta.setPageSize(pageable.getPageSize());   //Kích thước / 1 trang

        meta.setPages(pageUser.getTotalPages());    //Tổng số trang
        meta.setTotal(pageUser.getTotalElements()); //Tổng số bản ghi

        rs.setMeta(meta);
        rs.setResult(pageUser.getContent());
        return rs;
    }

    public User handleUpdateUser(User reqUser) {
        User currentUser = this.fetchUserById(reqUser.getId());
        if (currentUser != null) {
            currentUser.setEmail(reqUser.getEmail());
            currentUser.setName(reqUser.getName());
            currentUser.setPassword(reqUser.getPassword());
            // update
            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }
}
