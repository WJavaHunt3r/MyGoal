package com.ktk.duka.mygoal.service.user;

import com.ktk.duka.mygoal.service.utils.CrudService;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends CrudService<UserFilter, User, Long> {
    @Getter
    private UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    @Override
    public JpaRepository getRepository() {
        return repository;
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public Class<UserFilter> getFilterClass() {
        return UserFilter.class;
    }

    @Override
    public User createEntity() {
        return new User();
    }

    @Override
    public UserFilter createFilter() {
        return new UserFilter();
    }

    @Override
    public Page<User> fetchByQuery(UserFilter filter, Pageable pageable) {
        return repository.fetchByQuery(String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getUsername(),"")),
                String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getFirstname(),"")),
                String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getLastname(),"")), filter.getRole(), pageable);
    }

    @Override
    public long countByQuery(UserFilter filter) {
        return repository.countByQuery(String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getUsername(),"")),
                String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getFirstname(),"")),
                String.format("%%%s%%", ObjectUtils.defaultIfNull(filter.getLastname(),"")), filter.getRole());
    }

    public long countByUsername(String username){
        return repository.countByUsername(username);
    }

    public Optional<User> findByUsername(String username){
        return repository.findByUsername(username);
    }

    public List<User> findAllByBirthDateBetween(LocalDate from, LocalDate to){
        return repository.findAllByBirthDateBetween(from, to);
    }

    public List<User> findByU20(boolean isU20){
        return repository.findAllByU20(isU20);
    }
}
