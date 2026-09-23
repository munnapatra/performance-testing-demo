package com.mtech.report;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportDataController {

    private final ReportDataService service;

    public ReportDataController(ReportDataService service) {
        this.service = service;
    }

    @GetMapping("/{reportId}/data")
    public List<ReportData> getReportData(
            @PathVariable Long reportId) {

        return service.getReportData(reportId);
    }

    @GetMapping("/{reportId}/data/filter")
    public List<ReportData> getFilteredReportData(

            @PathVariable Long reportId,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        return service.getReportData(
                reportId,
                startDate,
                endDate
        );
    }
    
    @GetMapping("/{reportId}/data/page")
    public Page<ReportData> getReportDataPage(
            @PathVariable Long reportId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return service.getReportDataPage(
                reportId,
                LocalDate.parse(startDate),
                LocalDate.parse(endDate),
                pageable
        );
    }
}