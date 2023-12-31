package com.example.FlightAppDemo.Repositories;


import com.example.FlightAppDemo.DTO.User.UserDTO;
import com.example.FlightAppDemo.Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "BEGIN TRANSACTION;  \n" +
            "       INSERT INTO users (username, password, enabled, auth) VALUES (:#{#user.username}, :#{#user.password}, :#{#user.enabled}, :#{#user.auth}); " +
            " END; ", nativeQuery = true)
    int insertUser(@Param("user") UserDTO user);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "BEGIN TRANSACTION; " +
            "DELETE FROM users WHERE username = ?1; " +
            "END; ", nativeQuery = true)
    int deleteSelf(String e_mail);

    @Query(value = "SELECT * FROM \"users\" WHERE username = ?1", nativeQuery = true)
    Optional<User> getByEmail(String e_mail);

    @Query(value = "SELECT * FROM \"users\"", nativeQuery = true)
    List<User> getAll();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value =
            "UPDATE \"users\" SET enabled = true WHERE id IN ( SELECT id FROM  \"users\" WHERE \"username\" = ?1); ", nativeQuery = true)
    int EnableAccount(String username);
}
