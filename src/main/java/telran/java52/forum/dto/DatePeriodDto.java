package telran.java52.forum.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class DatePeriodDto {
    LocalDate dateFrom;
    LocalDate dateTo;
}
