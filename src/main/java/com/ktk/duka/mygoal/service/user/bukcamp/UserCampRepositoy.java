package com.ktk.duka.mygoal.service.user.bukcamp;

import com.ktk.duka.mygoal.service.bukcamp.BUKCamp;
import com.ktk.duka.mygoal.service.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserCampRepositoy extends JpaRepository<UserCamp, Long> {

    @Query("select uc from UserCamp uc WHERE (uc.user = ?1 or ?1 is null) and (uc.bukCamp = ?2 or ?2 is null)")
    Page<UserCamp> fetchByQuery(User user, BUKCamp camp, Pageable pageable);

    @Query("select count(uc) from UserCamp uc WHERE (uc.user = ?1 or ?1 is null) and (uc.bukCamp = ?2 or ?2 is null)")
    long countByQuery(User user, BUKCamp camp);
}
