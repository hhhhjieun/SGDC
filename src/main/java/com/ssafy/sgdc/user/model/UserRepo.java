package com.ssafy.sgdc.user.model;

import com.ssafy.sgdc.user.model.dto.UserLoginDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByLoginId(String loginId);
    boolean existsByUserNickname(String userNickname);

}
