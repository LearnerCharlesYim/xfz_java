package com.charles.xfz.repository;

import com.charles.xfz.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findByTelephone(String telephone);
}
