package com.ktk.duka.mygoal.service.bukcamp;

import com.ktk.duka.mygoal.service.utils.CrudService;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BUKCampService extends CrudService<BUKCampFilter, BUKCamp, Long> {

    @Getter
    private final BUKCampRepository repository;

    public BUKCampService(BUKCampRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<BUKCamp, Long> getRepository() {
        return repository;
    }

    @Override
    public Class<BUKCamp> getEntityClass() {
        return BUKCamp.class;
    }

    @Override
    public Class<BUKCampFilter> getFilterClass() {
        return BUKCampFilter.class;
    }

    @Override
    public BUKCamp CreateEntity() {
        return new BUKCamp();
    }

    @Override
    public BUKCampFilter CreateFilter() {
        return new BUKCampFilter();
    }

    @Override
    public long countByQuery(BUKCampFilter filter) {
        return repository.countByQuery(filter.getBukSeason(),
                String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getCampName(), "")),
                ObjectUtils.defaultIfNull(filter.getCampDateFrom(), LocalDate.now().minusYears(5)),
                ObjectUtils.defaultIfNull(filter.getCampDateTo(), LocalDate.now().plusYears(5)));
    }

    @Override
    public Page<BUKCamp> fetchByQuery(BUKCampFilter filter, Pageable pageable) {
        return repository.fetchByQuery(filter.getBukSeason(),
                String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getCampName(), "")),
                ObjectUtils.defaultIfNull(filter.getCampDateFrom(), LocalDate.now().minusYears(5)),
                ObjectUtils.defaultIfNull(filter.getCampDateTo(), LocalDate.now().plusYears(5)), pageable);
    }
}
