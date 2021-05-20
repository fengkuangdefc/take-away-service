package com.chenkesi.authorization.repository;

import com.chenkesi.authorization.domain.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends JpaSpecificationExecutor<User>, CrudRepository<User,String> {

    List<User> findByName(String name);

    User findByUsername(String username);

}
