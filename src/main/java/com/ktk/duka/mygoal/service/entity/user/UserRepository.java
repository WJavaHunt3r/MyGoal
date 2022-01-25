package com.ktk.duka.mygoal.service.entity.user;

import com.ktk.duka.mygoal.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {

    long countByUsername(String username);

    List<User> findAllByBirthDateBetween(LocalDate from, LocalDate to);

    List<User> findAllByU20(boolean isU20);

    Optional<User> findByUsername(String username);

    @Query("Select u FROM User u Where " +
            "(lower(u.username) LIKE lower(?1) or ?1 IS NULL OR ?1 LIKE '')"+
            " AND (lower(u.firstname) LIKE lower(?2) or ?2 IS NULL OR ?2 LIKE '')"+
            "AND (lower(u.lastname) LIKE lower(?3) or ?3 IS NULL OR ?3 LIKE '')" +
            "AND (u.role = ?4 or ?4 IS NULL)")
    Page<User> fetchByQuery(String username, String firstname, String lastname, Role role, Pageable pageable);

    @Query("Select COUNT (u) FROM User u Where " +
            "(lower(u.username) LIKE lower(?1) or ?1 IS NULL OR ?1 LIKE '')"+
            " AND (lower(u.firstname) LIKE lower(?2) or ?2 IS NULL OR ?2 LIKE '')"+
            "AND (lower(u.lastname) LIKE lower(?3) or ?3 IS NULL OR ?3 LIKE '')" +
            "AND (u.role = ?4 or ?4 IS NULL)")
    long countByQuery(String username, String firstname, String lastname, Role role);
}
