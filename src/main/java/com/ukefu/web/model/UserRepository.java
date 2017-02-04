package com.ukefu.web.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author 程序猿DD
 * @version 1.0.0
 * @date 16/3/23 下午2:34.
 * @blog http://blog.didispace.com
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(String id);

    User findByUsername(String username);
    
    Page<User> findAll(Pageable pageable);
}
