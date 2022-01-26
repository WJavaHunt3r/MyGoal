package com.ktk.duka.mygoal.service.team;

import com.ktk.duka.mygoal.service.user.User;
import com.ktk.duka.mygoal.service.utils.CrudEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "TEAMS")
@FieldNameConstants
public class Team extends CrudEntity<Team, Long> {
    @NotEmpty
    @NotNull
    @Column(name = "TEAM_NAME", length = 50)
    @Size(max = 50)
    private String teamName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "USERS")
    private User teamLeader;
    
}
