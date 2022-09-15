package com.ktk.duka.mygoal.service.user;

import com.ktk.duka.mygoal.enums.Role;
import com.ktk.duka.mygoal.service.team.Team;
import com.ktk.duka.mygoal.service.utils.CrudEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "USERS")
@FieldNameConstants
public class User extends CrudEntity<User, Long> {

    @Size(max = 50)
    @Column(name = "FIRSTNAME", length = 50)
    @NotNull
    @NotEmpty
    private String firstname;

    @Size(max = 50)
    @Column(name = "LASTNAME", length = 50)
    @NotNull
    @NotEmpty
    private String lastname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "TEAMS")
    private Team team;

    @Column(name = "ISU20")
    private boolean u20;

    @Size(max = 30)
    @Column(name = "USERNAME", length = 30)
    @NotNull
    @NotEmpty
    private String username;

    @Size(max = 1000)
    @Column(name = "PASSWORD", length = 1000)
    @NotNull
    @NotEmpty
    private String password;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @Column(name="MYSHARE_ID")
    @NotNull
    private long myShareID;

    public String getFullName(){
        return lastname + " " + firstname;
    }
}
