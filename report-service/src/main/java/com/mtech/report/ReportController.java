package com.mtech.report;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

	private final ReportService reportService;

	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}

	@GetMapping
	public List<Report> getReports() {
		return reportService.getActiveReports();
	}

	@GetMapping("/{id}")
	public Report getReport(@PathVariable Long id) {
		return reportService.getReport(id);
	}
}