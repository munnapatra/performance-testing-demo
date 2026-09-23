package com.mtech.report;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportDataRepository
        extends JpaRepository<ReportData, Long> {

    List<ReportData> findByReportId(Long reportId);

    List<ReportData> findByReportIdAndReportingDate(
            Long reportId,
            LocalDate reportingDate);

    List<ReportData> findByReportIdAndReportingDateBetween(
            Long reportId,
            LocalDate startDate,
            LocalDate endDate);
    
    Page<ReportData> findByReportIdAndReportingDateBetween(
            Long reportId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );
}