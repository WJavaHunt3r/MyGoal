package com.ktk.duka.mygoal.enums;

import com.vaadin.flow.component.UI;

public enum Role {
    ADMIN, USER, TEAM_LEADER;

    public String getCaption() {
        return UI.getCurrent().getTranslation(String.join(".", this.getDeclaringClass().getName(), this.name()));
    }

    public static boolean hasRestrictions(Role role) {
        return USER.equals(role) || TEAM_LEADER.equals(role);
    }
}
