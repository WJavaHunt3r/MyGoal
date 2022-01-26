package com.ktk.duka.mygoal.service.user.bukcamp;

import com.ktk.duka.mygoal.service.bukcamp.BUKCamp;
import com.ktk.duka.mygoal.service.user.User;
import com.ktk.duka.mygoal.service.utils.CrudEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "USER_CAMP")
public class UserCamp extends CrudEntity<UserCamp, Long> {

    @JoinColumn(name = "USER")
    @ManyToOne
    @NotNull
    private User user;

    @JoinColumn(name = "BUK_CAMP")
    @ManyToOne
    @NotNull
    private BUKCamp bukCamp;

    @Column(name = "CREATED_AT")
    @CreationTimestamp
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
