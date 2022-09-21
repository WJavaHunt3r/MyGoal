package com.ktk.duka.mygoal.service.bukseason;

import com.ktk.duka.mygoal.service.utils.CrudService;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BUKSeasonService extends CrudService<BUKSeasonFilter, BUKSeason, Long> {
    @Getter
    private final BUKSeasonRepository repository;

    public BUKSeasonService(BUKSeasonRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<BUKSeason, Long> getRepository() {
        return repository;
    }

    @Override
    public Class<BUKSeason> getEntityClass() {
        return BUKSeason.class;
    }

    @Override
    public Class<BUKSeasonFilter> getFilterClass() {
        return BUKSeasonFilter.class;
    }

    @Override
    public BUKSeason createEntity() {
        return new BUKSeason();
    }

    @Override
    public BUKSeasonFilter createFilter() {
        return new BUKSeasonFilter();
    }

    @Override
    public Page<BUKSeason> fetchByQuery(BUKSeasonFilter filter, Pageable pageable){
        return repository.fetchByQuery(String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getSeasonName(), "")), pageable);
    }

    @Override
    public long countByQuery(BUKSeasonFilter filter){
        return repository.countByQuery(String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getSeasonName(), "")));
    }

    public Optional<BUKSeason> findBUKSeasonBySeasonName(String seasonName){
        return repository.findBUKSeasonBySeasonName(seasonName);
    }

    public long countBySeasonName(String seasonName){
        return repository.countBySeasonName(seasonName);
    }
}
