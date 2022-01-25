package com.ktk.duka.mygoal.security;

import com.ktk.duka.mygoal.enums.Role;
import com.ktk.duka.mygoal.service.entity.user.User;
import com.ktk.duka.mygoal.service.entity.user.UserService;
import com.vaadin.flow.server.VaadinServletRequest;
import lombok.Getter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService {

    @Getter
    private final UserService userService;

    public SecurityService(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return userService.findByUsername(((UserDetails) principal).getUsername());
        }
        return Optional.empty();
    }

    public boolean accessGranted(Class<?> navigationTarget) {
        Role role = getAuthenticatedUser().map(User::getRole).orElse(null);
        return !Role.hasRestrictions(role) || Permissions.hasPermission(role, navigationTarget);
    }

    public boolean hasRestrictions() {
        return Role.hasRestrictions(getAuthenticatedUser().map(User::getRole).orElse(null));
    }

    public boolean isAuthenticated(User user) {
        return getAuthenticatedUser().map(User::getUsername).orElse("").equals(user.getUsername());
    }

    public String encryptSecret(String secret) {
        return SecurityUtils.encryptSecret(secret);
    }

    public void logout() {
        new SecurityContextLogoutHandler().logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }

}
