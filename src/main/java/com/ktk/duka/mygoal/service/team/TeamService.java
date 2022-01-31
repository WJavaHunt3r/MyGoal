package com.ktk.duka.mygoal.service.team;

import com.ktk.duka.mygoal.service.utils.CrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamService extends CrudService<TeamFilter, Team, Long> {
    private TeamRepository repository;

    public TeamService(TeamRepository repository){
        this.repository = repository;
    }

    @Override
    public JpaRepository getRepository() {
        return repository;
    }

    @Override
    public Class<Team> getEntityClass() {
        return Team.class;
    }

    @Override
    public Class<TeamFilter> getFilterClass() {
        return TeamFilter.class;
    }

    @Override
    public Team CreateEntity() {
        return new Team();
    }

    @Override
    public TeamFilter CreateFilter() {
        return new TeamFilter();
    }
}
