package com.ktk.duka.mygoal.service.bukseason;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BUKSeasonRepository extends JpaRepository<BUKSeason, Long> {

    Optional<BUKSeason> findBUKSeasonBySeasonName(String seasonName);

    @Query("select s FROM BUKSeason s where (lower(s.seasonName) LIKE lower(?1) or ?1 is null or ?1 like '')")
    Page<BUKSeason> fetchByQuery(String seasonName, Pageable pageable);

    @Query("select count(s) FROM BUKSeason s where (lower(s.seasonName) LIKE lower(?1) or ?1 is null or ?1 like '')")
    long countByQuery(String seasonName);

    long countBySeasonName(String seasonName);
}
