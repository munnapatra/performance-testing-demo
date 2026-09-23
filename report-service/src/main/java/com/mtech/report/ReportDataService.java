package com.mtech.report;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReportDataService {

    private final ReportDataRepository repository;

    public ReportDataService(ReportDataRepository repository) {
        this.repository = repository;
    }

    public List<ReportData> getReportData(Long reportId) {
        return repository.findByReportId(reportId);
    }

    public List<ReportData> getReportData(
            Long reportId,
            LocalDate startDate,
            LocalDate endDate) {

        return repository.findByReportIdAndReportingDateBetween(
                reportId,
                startDate,
                endDate
        );
    }
    
    public Page<ReportData> getReportDataPage(
            Long reportId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable) {

        return repository
                .findByReportIdAndReportingDateBetween(
                        reportId,
                        startDate,
                        endDate,
                        pageable
                );
    }
}