package com.ktk.duka.mygoal.service.bukcamp;

import com.ktk.duka.mygoal.service.bukseason.BUKSeason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BUKCampRepository extends JpaRepository<BUKCamp, Long> {

    @Query("SELECT c from BUKCamp c where c.season = ?1 " +
            "AND (lower(c.campName) LIKE ?2 or ?2 is NULL or ?2 like '') " +
            "AND (c.campDate BETWEEN ?3 and ?4)")
    Page<BUKCamp> fetchByQuery(BUKSeason bukSeason, String campName, LocalDate campDateFrom, LocalDate campDateTo, Pageable pageable);

    @Query("SELECT count(c) from BUKCamp c where c.season = ?1 " +
            "AND (lower(c.campName) LIKE ?2 or ?2 is NULL or ?2 like '') " +
            "AND (c.campDate BETWEEN ?3 and ?4)")
    long countByQuery(BUKSeason bukSeason, String campName, LocalDate campDateFrom, LocalDate campDateTo);


}
