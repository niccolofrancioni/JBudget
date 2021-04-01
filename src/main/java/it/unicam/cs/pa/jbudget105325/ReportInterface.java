package it.unicam.cs.pa.jbudget105325;

import java.time.LocalDate;

public interface ReportInterface<T extends Account> {
    Report generateReport(T account, Tag tag);

    Report generateReport(T account, Tag tag, LocalDate fromDate, LocalDate toDate);
}
