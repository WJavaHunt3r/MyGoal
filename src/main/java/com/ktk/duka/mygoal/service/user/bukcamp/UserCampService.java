package com.ktk.duka.mygoal.service.user.bukcamp;

import com.ktk.duka.mygoal.service.utils.CrudService;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public class UserCampService extends CrudService<UserCampFilter, UserCamp, Long> {

    @Getter
    private final UserCampRepositoy repositoy;

    public UserCampService(UserCampRepositoy repositoy) {
        this.repositoy = repositoy;
    }

    @Override
    public JpaRepository<UserCamp, Long> getRepository() {
        return repositoy;
    }

    @Override
    public Class<UserCamp> getEntityClass() {
        return UserCamp.class;
    }

    @Override
    public Class<UserCampFilter> getFilterClass() {
        return UserCampFilter.class;
    }

    @Override
    public UserCamp CreateEntity() {
        return new UserCamp();
    }

    @Override
    public UserCampFilter CreateFilter() {
        return new UserCampFilter();
    }

    @Override
    public long countByQuery(UserCampFilter filter) {
        return repositoy.countByQuery(filter.getUser(), filter.getCamp());
    }

    @Override
    public Page<UserCamp> fetchByQuery(UserCampFilter filter, Pageable pageable) {
        return repositoy.fetchByQuery(filter.getUser(), filter.getCamp(), pageable);
    }
}
