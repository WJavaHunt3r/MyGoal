package com.ktk.duka.mygoal.service.bukseason;

import com.ktk.duka.mygoal.service.utils.CrudEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "BUK_SEASON")
@Getter
@Setter
@FieldNameConstants
public class BUKSeason extends CrudEntity<BUKSeason, Long> {

    @NotNull
    @NotEmpty
    @Column(name = "SEASON_NAME", length = 30)
    @Size(max = 30)
    private String seasonName;

    @NotNull
    @Column(name = "START_DATE")
    private LocalDate startDate;

    @NotNull
    @Column(name = "END_DATE")
    private LocalDate endDate;
}
