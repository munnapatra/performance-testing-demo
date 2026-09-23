package com.mtech.report;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "report_data")
public class ReportData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_id", nullable = false)
    private Long reportId;

    @Column(name = "reporting_date", nullable = false)
    private LocalDate reportingDate;

    @Column(name = "business_unit", nullable = false)
    private String businessUnit;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String status;

    public ReportData() {
    }

    public ReportData(
            Long reportId,
            LocalDate reportingDate,
            String businessUnit,
            String currency,
            BigDecimal amount,
            String status) {

        this.reportId = reportId;
        this.reportingDate = reportingDate;
        this.businessUnit = businessUnit;
        this.currency = currency;
        this.amount = amount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getReportId() {
        return reportId;
    }

    public LocalDate getReportingDate() {
        return reportingDate;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public void setReportingDate(LocalDate reportingDate) {
        this.reportingDate = reportingDate;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}