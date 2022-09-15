package com.ktk.duka.mygoal.security;

import com.ktk.duka.mygoal.enums.Role;
import com.ktk.duka.mygoal.views.login.LoginView;
import com.ktk.duka.mygoal.views.logout.LogoutView;
import com.ktk.duka.mygoal.views.status.StatusView;
import com.ktk.duka.mygoal.views.user.UserView;
import com.vaadin.flow.component.Component;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
class Permissions {

    private static final Map<Role, List<Class<? extends Component>>> VIEW_PERMISSIONS = new HashMap<>();

    static {
        VIEW_PERMISSIONS.put(Role.ADMIN, List.of(
                LoginView.class, UserView.class, StatusView.class, LogoutView.class));

        VIEW_PERMISSIONS.put(Role.USER, List.of(
                LoginView.class, StatusView.class, LogoutView.class));
        VIEW_PERMISSIONS.put(Role.TEAM_LEADER, List.of(
                LoginView.class, StatusView.class, LogoutView.class));
    }

    static boolean hasPermission(Role role, Class<?> navigationTarget) {
        return VIEW_PERMISSIONS.getOrDefault(role, Collections.emptyList()).contains(navigationTarget);
    }
}
