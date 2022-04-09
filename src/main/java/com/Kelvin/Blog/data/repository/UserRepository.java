package com.Kelvin.Blog.data.repository;


import com.Kelvin.Blog.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.firstName =?1 and u.lastName =?2")
    List<User> findUserByFirstNameAndLastName(String firstName, String lastName) ;
}
