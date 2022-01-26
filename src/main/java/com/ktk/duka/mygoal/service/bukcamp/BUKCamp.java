package com.ktk.duka.mygoal.service.bukcamp;

import com.ktk.duka.mygoal.service.bukseason.BUKSeason;
import com.ktk.duka.mygoal.service.utils.CrudEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "BUK_CAMP")
@FieldNameConstants
public class BUKCamp extends CrudEntity<BUKCamp, Long> {
    @NotNull
    @JoinColumn(name = "BUK_SEASON")
    @ManyToOne
    private BUKSeason season;

    @NotNull
    @NotEmpty
    @Column(name = "CAMP_NAME", length = 30)
    @Size(max = 30)
    private String campName;

    @NotNull
    @Column(name = "CAMP_DATE")
    private LocalDate campDate;

    @NotNull
    @Column(name = "FINANCE_CHECK_DATE")
    private LocalDate financeCheckDate;

    @NotNull
    @Column(name = "U18_LIVE_BRUNSTAD_FEE")
    private Integer u18LiveBrunstadFee;

    @NotNull
    @Column(name = "U18_LIVE_LOCAL_FEE")
    private Integer u18LiveTravelFee;

    @NotNull
    @Column(name = "U18_LOCAL_BRUNSTAD_FEE")
    private Integer u18LocalBrunstadFee;

    @NotNull
    @Column(name = "U18_LOCAL_LOCAL_FEE")
    private Integer u18LocalLocalFee;

    @NotNull
    @Column(name = "O18_LIVE_BRUNSTAD_FEE")
    private Integer o18LiveBrunstadFee;

    @NotNull
    @Column(name = "O18_LIVE_LOCAL_FEE")
    private Integer o18LiveTravelFee;

    @NotNull
    @Column(name = "O18_LOCAL_BRUNSTAD_FEE")
    private Integer o18LocalBrunstadFee;

    @NotNull
    @Column(name = "O18_LOCAL_LOCAL_FEE")
    private Integer o18LocalLocalFee;
}
