package com.ktk.duka.mygoal.service.bukcamp;

import com.ktk.duka.mygoal.service.bukseason.BUKSeason;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Data
@FieldNameConstants
public class BUKCampFilter {

    private BUKSeason bukSeason;

    private String campName;

    private LocalDate campDateFrom;

    private LocalDate campDateTo;
}
