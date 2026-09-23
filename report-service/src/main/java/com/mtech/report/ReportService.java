package com.mtech.report;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> getActiveReports() {
        return reportRepository.findByActiveTrue();
    }

    public Report getReport(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Report not found: " + id));
    }
}