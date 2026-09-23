package com.mtech.report;

import jakarta.persistence.*;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String reportName;

    @Column(nullable = false)
    private String reportType;

    private boolean active;

    public Report() {
    }

    public Report(String reportName, String reportType, boolean active) {
        this.reportName = reportName;
        this.reportType = reportType;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getReportName() {
        return reportName;
    }

    public String getReportType() {
        return reportType;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}